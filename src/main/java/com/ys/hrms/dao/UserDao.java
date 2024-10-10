package com.ys.hrms.dao;

import com.ys.hrms.entity.User;
import com.ys.hrms.exception.UserNotFoundException;
import com.ys.hrms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserDao {

    private final UserRepository userRepository;

    @Autowired
    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> saveUsers(List<User> users) {
        return userRepository.saveAll(users);
    }

    public User findUserById(int id) {
    	return userRepository.findById(id).orElseThrow(() -> 
            new UserNotFoundException("User not found with ID: " + id));
    }

    public List<User> findByOrganizationId(int organizationId) {
        return userRepository.findByOrganizationId(organizationId);
    }

    public User updateUser(int id, User updatedUser) {
        User existingUser = findUserById(id);
        existingUser.setEmployeeCode(updatedUser.getEmployeeCode());
        existingUser.setDepartment(updatedUser.getDepartment());
        existingUser.setDesignation(updatedUser.getDesignation());
        existingUser.setBranch(updatedUser.getBranch());
        existingUser.setOfficialEmail(updatedUser.getOfficialEmail());
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(updatedUser.getPassword()); // Ensure it's hashed
        existingUser.setRole(updatedUser.getRole());
        existingUser.setNoticePeriod(updatedUser.getNoticePeriod());
        return userRepository.save(existingUser);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
