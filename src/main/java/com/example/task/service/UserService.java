package com.example.task.service;

import com.example.task.config.JwtConfig;
import com.example.task.dto.request.LoginRequest;
import com.example.task.dto.request.RegisterUserRequest;
import com.example.task.dto.response.LoginResponse;
import com.example.task.dto.response.RegisterUserResponse;
import com.example.task.dto.response.BaseResponse;
import com.example.task.model.Users;
import com.example.task.repository.UsersRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    JwtConfig jwtConfig;

    public ResponseEntity<BaseResponse> registerUser(RegisterUserRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            if (usersRepo.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Email sudah digunakan");
            }
            if (usersRepo.findByUsername(request.getUsername()).isPresent()) {
                throw new RuntimeException("Username sudah digunakan");
            }

            var user = Users.builder()
                    .username(request.getUsername())
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(request.getPassword()) // anggap sudah di encrypt ketika dikirim
                    .createdAt(LocalDateTime.now())
                    .build();
            usersRepo.save(user);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Success add user");
            response.setData(RegisterUserResponse.builder().userId(user.getUserId()).build());

            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            log.error("Error in registerUser, message : " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<BaseResponse> login(LoginRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            var user = usersRepo.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Email not found"));

            // anggap password sudah di-encrypt sebelum dikirim
            if (!user.getPassword().equals(request.getPassword())) {
                throw new RuntimeException("Password salah");
            }

            String token = jwtConfig.generateToken(user.getUserId());

            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Login berhasil");
            response.setData(LoginResponse.builder().token(token).build());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in login, message : " + e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Something wrong happened");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
