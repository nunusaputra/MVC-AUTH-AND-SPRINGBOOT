// package com.example.job.controller.api;

// import java.security.Principal;
// import java.util.Collection;
// import java.util.Date;
// import java.util.LinkedList;
// import java.util.List;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import
// org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.job.dto.ChangePassDTO;
// import com.example.job.dto.ForgotPassDTO;
// import com.example.job.dto.LoginDTO;
// import com.example.job.dto.RegisterDTO;
// import com.example.job.dto.UserDTO;
// import com.example.job.model.Person;
// import com.example.job.model.ResetPassword;
// import com.example.job.model.Role;
// import com.example.job.model.Users;
// import com.example.job.repository.ResetRepository;
// import com.example.job.repository.RoleRepository;
// import com.example.job.repository.UsersRepository;
// import com.example.job.utils.CustomResponse;
// import com.example.job.utils.EmailServices;

// @RestController
// @RequestMapping("/auth")
// public class AuthRestController {
// private UsersRepository usersRepository;
// private RoleRepository roleRepository;
// private EmailServices emailServices;
// private PasswordEncoder passwordEncoder;
// private ResetRepository resetRepository;

// @Autowired
// public AuthRestController(UsersRepository usersRepository, RoleRepository
// roleRepository,
// EmailServices emailServices, PasswordEncoder passwordEncoder, ResetRepository
// resetRepository) {
// this.usersRepository = usersRepository;
// this.roleRepository = roleRepository;
// this.emailServices = emailServices;
// this.passwordEncoder = passwordEncoder;
// this.resetRepository = resetRepository;
// }

// // * API REGISTRATION ACCOUNT
// @PostMapping("register")
// public ResponseEntity<Object> post(@RequestBody RegisterDTO regis) {
// try {
// // TODO: CHECK IF EMAIL IS ALREADY EXISTS
// if (usersRepository.findByEmail(regis.getEmail()).isPresent()) {
// return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Email is already
// exists!");
// }

// // TODO: CHECK IF PASSWORD AND CONFIRM PASSWORD DOESN'T MATCH
// if (!regis.getPassword().equals(regis.getConfPassword())) {
// return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Password and confirm
// password not match!");
// }

// // TODO: SEARCH THE LOWEST LEVEL OF ROLE
// Role role = roleRepository.findByLevel(2);

// Users users = new Users();
// Person person = users.getPerson();

// if (person == null) {
// person = new Person();
// users.setPerson(person);
// }

// person.setFullname(regis.getFullname());
// person.setNickname(regis.getNickname());
// person.setUsers(users);

// users.setEmail(regis.getEmail());
// users.setPassword(passwordEncoder.encode(regis.getPassword()));
// users.setRole(role);

// String body = "Halo " + regis.getFullname()
// + ", Selamat akun anda berhasil dibuat, silahkan untuk melakukan login ke
// link berikut http://localhost:8080/user/login";

// emailServices.sendMail(regis.getEmail(), "Account Registered", body);

// return CustomResponse.generate(HttpStatus.CREATED, "Success create new
// account",
// usersRepository.save(users));
// } catch (Exception e) {
// return CustomResponse.generate(HttpStatus.INTERNAL_SERVER_ERROR, "Failed
// create new account");
// }

// }

// // * API LOGIN ACCOUNT
// @PostMapping("login")
// public ResponseEntity<Object> post(@RequestBody LoginDTO login) {
// UserDTO users = usersRepository.getUsers(login.getEmail());

// if (users != null && passwordEncoder.matches(login.getPassword(),
// users.getPassword())) {
// try {
// User userDetails = new User(
// users.getId().toString(),
// "",
// getAuthorize(users.getName()));
// PreAuthenticatedAuthenticationToken authenticationToken = new
// PreAuthenticatedAuthenticationToken(
// userDetails,
// "",
// userDetails.getAuthorities());
// SecurityContextHolder.getContext().setAuthentication(authenticationToken);
// } catch (Exception e) {
// e.printStackTrace();
// }
// } else {
// return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Email atau password
// salah!");
// }

// return CustomResponse.generate(HttpStatus.OK, "Login Successfully");
// }

// private static Collection<? extends GrantedAuthority> getAuthorize(String
// role) {
// final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
// authorities.add(new SimpleGrantedAuthority(role));
// return authorities;
// }

// // * API Untuk Change Password
// @PutMapping("change-password")
// public ResponseEntity<Object> put(@RequestBody ChangePassDTO change,
// Principal principal) {
// try {
// String id = principal.getName();
// Integer userId = Integer.parseInt(id);

// Users user = usersRepository.findById(userId).orElse(null);

// // TODO: CHECK IF OLD PASSWORD DOESN'T MATCH WITH PASSWORD IN DB
// if (!passwordEncoder.matches(change.getOldPassword(), user.getPassword())) {
// return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Old password doesn't
// match!");
// }

// // TODO: CHECK IF PASSWORD AND CONF PASSWORD NOT MATCH
// if (!change.getNewPassword().equals(change.getConfPassword())) {
// return CustomResponse.generate(HttpStatus.BAD_REQUEST, "New password and
// confirm password not match!");
// }

// user.setPassword(passwordEncoder.encode(change.getNewPassword()));
// usersRepository.save(user);

// String body = "Halo " + user.getPerson().getFullname()
// + ", Selamat password anda berhasil diubah, ini adalah password anda yang
// baru: "
// + change.getNewPassword();

// emailServices.sendMail(user.getEmail(), "Successfully Change Password",
// body);
// return CustomResponse.generate(HttpStatus.OK, "Successfully change a new
// password");
// } catch (Exception e) {
// return CustomResponse.generate(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to
// change a password");
// }
// }

// // * API Untuk Forgot Password
// @PostMapping("forgot-password")
// public ResponseEntity<Object> post(@RequestBody ForgotPassDTO forgot) {
// try {
// Users user = usersRepository.findByEmail(forgot.getEmail()).orElse(null);

// if (user == null) {
// return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Email doesn't
// exists!");
// }

// String token = UUID.randomUUID().toString();

// ResetPassword reset = new ResetPassword();
// reset.setUserId(user.getId());
// reset.setToken(token);
// reset.setExpiryDate(new Date(System.currentTimeMillis() + 30 * 60 * 1000));
// resetRepository.save(reset);

// String linkToken =
// "http://localhost:8080/api/auth/forgot-password/reset?token=" + token;
// String body = "Halo " + user.getPerson().getFullname()
// + ", Silahkan untuk melakukan reset password pada link berikut: "
// + linkToken;

// // TODO: PART TO SEND A MAIL AFTER SUCCESS REGISTRATION
// emailServices.sendMail(user.getEmail(), "Email Reset Password", body);

// return CustomResponse.generate(HttpStatus.OK, "Cek your email to get a reset
// password link");
// } catch (Exception e) {
// return CustomResponse.generate(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to
// reset a password");
// }
// }

// @PutMapping("forgot-password/reset")
// public ResponseEntity<Object> put(@RequestParam("token") String token,
// @RequestBody ForgotPassDTO reset) {
// try {
// ResetPassword resetToken = resetRepository.findByToken(token);

// // TODO: CHECK IF TOKEN DOESN'T EXISTS OR TOKEN HAS EXPIRED
// if (resetToken == null || resetToken.getExpiryDate().before(new Date()) ||
// resetToken.isUsed()) {
// return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Token isn't valid or
// expired!");
// }

// Users user = usersRepository.findById(resetToken.getUserId()).orElse(null);

// // TODO: CHECK IF USER IS NULL
// if (user == null) {
// return CustomResponse.generate(HttpStatus.OK, "User not found!");
// }

// user.setPassword(passwordEncoder.encode(reset.getNewPassword()));
// usersRepository.save(user);

// resetToken.setUsed(true);
// resetRepository.save(resetToken);

// String body = "Halo " + user.getPerson().getFullname() + ",\n\n"
// + "Password Anda berhasil direset.";
// emailServices.sendMail(user.getEmail(), "Successfully Reset Password", body);

// return CustomResponse.generate(HttpStatus.OK, "Successfully reset a
// password");
// } catch (Exception e) {
// return CustomResponse.generate(HttpStatus.INTERNAL_SERVER_ERROR, "Failed
// reset a password!");
// }
// }
// }
