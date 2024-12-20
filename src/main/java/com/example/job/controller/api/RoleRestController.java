package com.example.job.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.job.model.Role;
import com.example.job.repository.RoleRepository;
import com.example.job.utils.CustomResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api")
public class RoleRestController {
    private RoleRepository roleRepository;

    @Autowired
    public RoleRestController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("role")
    public ResponseEntity<Object> get() {
        return CustomResponse.generate(HttpStatus.OK, "data berhasil di dapatkan", roleRepository.findAll());
    }

    @GetMapping("role/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) Integer id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role != null) {
            return CustomResponse.generate(HttpStatus.OK, "data berhasil di dapatkan", role);
        } else {
            return CustomResponse.generate(HttpStatus.OK, "data tidak berhasil di dapatkan");
        }
    }

    @PostMapping("role")
    public ResponseEntity<Object> post(@RequestBody Role role) {
        return CustomResponse.generate(HttpStatus.OK, "data berhasil disimpan", roleRepository.save(role));
    }

    @PutMapping("role/{id}")
    public ResponseEntity<Object> put(@PathVariable Integer id, @RequestBody Role role) {
        if (!roleRepository.existsById(id)) {
            return CustomResponse.generate(HttpStatus.OK, "Role tidak ditemukan");
        }

        Role putRole = roleRepository.save(role);
        return CustomResponse.generate(HttpStatus.OK, "data berhasil diubah", putRole);
    }

    @DeleteMapping("role/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Integer id) {
        Boolean roleExist = roleRepository.existsById(id);

        if (!roleExist) {
            return CustomResponse.generate(HttpStatus.OK, "data tidak ditemukan");
        }

        roleRepository.deleteById(id);
        return CustomResponse.generate(HttpStatus.OK, "data berhasil dihapus");
    }

}
