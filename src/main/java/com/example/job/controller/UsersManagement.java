// package com.example.job.controller;

// import java.security.Principal;
// import java.util.Date;
// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.List;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import
// org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.example.job.dto.ChangePassDTO;
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
// import com.example.job.utils.EmailServices;
// import com.example.job.utils.ErrorHandling;

// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// @ControllerAdvice
// @Controller
// @RequestMapping("user")
// public class UsersManagement {
// private UsersRepository usersRepository;
// private PasswordEncoder passwordEncoder;
// private RoleRepository roleRepository;
// private EmailServices emailServices;
// private ResetRepository resetRepository;

// @Autowired
// public UsersManagement(UsersRepository usersRepository, PasswordEncoder
// passwordEncoder,
// RoleRepository roleRepository, EmailServices emailServices, ResetRepository
// resetRepository) {
// this.usersRepository = usersRepository;
// this.passwordEncoder = passwordEncoder;
// this.roleRepository = roleRepository;
// this.emailServices = emailServices;
// this.resetRepository = resetRepository;
// }

// // * Routing to dashboard
// @GetMapping("dashboard")
// public String dashboard(Model model) {
// return "user/dashboard";
// }

// // * Routing to register form
// @GetMapping("register")
// public String register(Model model) {
// model.addAttribute("register", new RegisterDTO());
// return "/user/register";
// }

// // * Routing to save registration
// @PostMapping("registration")
// public String registration(ErrorHandling err, RegisterDTO regis, Model model,
// RedirectAttributes redirectAttributes) {
// try {
// // TODO: CHECK IF EMAIL EXISTS IN DATABASE
// if (usersRepository.findByEmail(regis.getEmail()).isPresent()) {
// throw new ErrorHandling("Email is already exists");
// }

// // TODO: CHECK IF PASSWORD AND CONFIRM PASSWORD DOES'T MATCH
// if (!regis.getPassword().equals(regis.getConfPassword())) {
// throw new ErrorHandling("Password and confirm password not match");
// }

// // TODO: SEARCH THE LOWEST LEVEL OF ROLE
// Role defaultRole = roleRepository.findByLevel(2);

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
// users.setRole(defaultRole);

// usersRepository.save(users);

// String body = "Halo " + regis.getFullname()
// + ", Selamat akun anda berhasil dibuat, silahkan untuk melakukan login ke
// link berikut http://localhost:8080/user/login";

// // TODO: PART TO SEND A MAIL AFTER SUCCESS REGISTRATION
// emailServices.sendMail(regis.getEmail(), "Account Registered", body);

// redirectAttributes.addFlashAttribute("successMessage", "Successfully create
// an account");
// return "redirect:/user/login";
// } catch (ErrorHandling e) {
// redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
// return "redirect:/user/register";
// }
// }

// // * Routing to login form
// @GetMapping("login")
// public String index(Model model) {
// model.addAttribute("login", new LoginDTO());
// return "user/login";
// }

// // * Routing to save login
// @PostMapping("authenticate")
// public String authenticate(LoginDTO login, ErrorHandling err,
// RedirectAttributes redirectAttributes) {
// UserDTO user = usersRepository.getUsers(login.getEmail());

// if (user != null && passwordEncoder.matches(login.getPassword(),
// user.getPassword())) {
// try {
// User userDetails = new User(
// user.getId().toString(),
// "",
// getAuthorities(user.getName()));
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
// redirectAttributes.addFlashAttribute("errorMessage", "Email atau password
// salah!");
// return "redirect:/user/login";
// }
// redirectAttributes.addFlashAttribute("successMessage", "Login Successfully");
// return "redirect:/user/dashboard";
// }

// private static Collection<? extends GrantedAuthority> getAuthorities(String
// role) {
// final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
// authorities.add(new SimpleGrantedAuthority(role));
// return authorities;
// }

// // * Routing to change password form
// @GetMapping("change-pass")
// public String changePass(Model model) {
// model.addAttribute("changePass", new ChangePassDTO());
// return "user/changePass";
// }

// // * Routing to save change password
// @PostMapping("change")
// public String saveChange(RedirectAttributes redirectAttributes,
// ChangePassDTO pass, ErrorHandling err, Principal principal) {
// try {
// String id = principal.getName();
// Integer userId = Integer.parseInt(id);

// Users user = usersRepository.findById(userId).orElse(null);

// // TODO: CHECK IF OLD PASSWORD DOESN'T MATCH WITH PASSWORD IN DB
// if (!passwordEncoder.matches(pass.getOldPassword(), user.getPassword())) {
// throw new ErrorHandling("Old password doesn't match!");
// }

// // TODO: CHECK IF NEW PASSWORD AND CONFIRM PASSWORD NOT MATCH
// if (!pass.getNewPassword().equals(pass.getConfPassword())) {
// throw new ErrorHandling("New password and confirm password not match!");
// }

// user.setPassword(passwordEncoder.encode(pass.getNewPassword()));
// usersRepository.save(user);

// String body = "Halo " + user.getPerson().getFullname()
// + ", Selamat password anda berhasil diubah, ini adalah password anda yang
// baru: "
// + pass.getNewPassword();

// // TODO: PART TO SEND A MAIL AFTER SUCCESS REGISTRATION
// emailServices.sendMail(user.getEmail(), "Successfully Change Password",
// body);

// redirectAttributes.addFlashAttribute("successMessage", "Successfully change a
// password");
// return "redirect:/user/dashboard";
// } catch (Exception e) {
// redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
// return "redirect:/user/change-pass";
// }
// }

// // * Routing to forgot password form
// @GetMapping("forgot-password")
// public String forgot(Model model) {
// model.addAttribute("forgot", new LoginDTO());
// return "user/forgotPass";
// }

// @PostMapping("forgot-password/validate")
// public String forgotValidate(LoginDTO validate, RedirectAttributes
// redirectAttributes, ErrorHandling err) {
// try {
// Users user = usersRepository.findUser(validate.getEmail());

// if (user == null) {
// throw new ErrorHandling("Email tidak ada!");
// }

// String token = UUID.randomUUID().toString();

// ResetPassword reset = new ResetPassword();
// reset.setUserId(user.getId());
// reset.setToken(token);
// reset.setExpiryDate(new Date(System.currentTimeMillis() + 30 * 60 * 1000));
// resetRepository.save(reset);

// String linkToken = "http://localhost:8080/user/forgot-password/reset?token="
// + token;
// String body = "Halo " + user.getPerson().getFullname()
// + ", Silahkan untuk melakukan reset password pada link berikut: "
// + linkToken;

// // TODO: PART TO SEND A MAIL AFTER SUCCESS REGISTRATION
// emailServices.sendMail(user.getEmail(), "Email Reset Password", body);

// redirectAttributes.addFlashAttribute("successMessage", "Silahkan cek email
// anda");
// return "redirect:/user/forgot-password";
// } catch (ErrorHandling e) {
// redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
// return "redirect:/user/login";
// }
// }

// @GetMapping("forgot-password/reset")
// public String reset(@PathVariable("token") String token, Model model,
// RedirectAttributes redirectAttributes) {
// ResetPassword reset = resetRepository.findByToken(token);

// if (reset == null || reset.getExpiryDate().before(new Date()) ||
// reset.isUsed()) {
// redirectAttributes.addFlashAttribute("errorMessage", "Token tidak valid atau
// sudah kedaluwarsa.");
// return "redirect:/user/forgot-password";
// }

// model.addAttribute("token", token);
// model.addAttribute("reset", new LoginDTO());
// return "user/reset";
// }

// @PostMapping("forgot-password/reset")
// public String saveReset(@RequestParam("token") String token, LoginDTO pass,
// RedirectAttributes redirectAttributes) {
// try {
// ResetPassword resetToken = resetRepository.findByToken(token);

// if (resetToken == null || resetToken.getExpiryDate().before(new Date()) ||
// resetToken.isUsed()) {
// throw new RuntimeException("Token tidak valid atau sudah kedaluwarsa.");
// }

// Users user = usersRepository.findById(resetToken.getUserId())
// .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

// user.setPassword(passwordEncoder.encode(pass.getPassword()));
// usersRepository.save(user);

// resetToken.setUsed(true);
// resetRepository.save(resetToken);

// String body = "Halo " + user.getPerson().getFullname() + ",\n\n"
// + "Password Anda berhasil direset.";
// emailServices.sendMail(user.getEmail(), "Password Berhasil Direset", body);

// redirectAttributes.addFlashAttribute("successMessage",
// "Password berhasil direset. Silahkan login kembali.");
// return "redirect:/user/login";
// } catch (Exception e) {
// redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
// return "redirect:/user/forgot-password";
// }
// }

// }
