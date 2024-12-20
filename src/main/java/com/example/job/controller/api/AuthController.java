// package com.example.job.controller.api;

// import java.util.Collections;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.job.dto.LoginDTO;
// import com.example.job.dto.RegisterDTO;
// import com.example.job.model.Person;
// import com.example.job.model.Role;
// import com.example.job.model.Users;
// import com.example.job.repository.RoleRepository;
// import com.example.job.repository.UsersRepository;
// import com.example.job.security.JWTUtil;

// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {
// @Autowired
// private UsersRepository userRepo;
// @Autowired
// JWTUtil jwtUtil;
// @Autowired
// AuthenticationManager authManager;
// @Autowired
// PasswordEncoder passwordEncoder;
// @Autowired
// RoleRepository roleRepository;

// @PostMapping("/register")
// public Map<String, Object> registerHandler(@RequestBody RegisterDTO regis) {
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

// userRepo.save(users);

// String token = jwtUtil.generateToken(users.getEmail());

// return Collections.singletonMap("jwt-token", token);
// }

// @PostMapping("/login")
// public Map<String, Object> loginHandler(@RequestBody LoginDTO login) {
// try {
// UsernamePasswordAuthenticationToken authInputToken = new
// UsernamePasswordAuthenticationToken(
// login.getEmail(), login.getPassword());

// authManager.authenticate(authInputToken);

// String token = jwtUtil.generateToken(login.getEmail());

// return Collections.singletonMap("jwt-token", token);
// } catch (AuthenticationException authExc) {
// throw new RuntimeException("Invalid Login Credentials");
// }
// }

// }
