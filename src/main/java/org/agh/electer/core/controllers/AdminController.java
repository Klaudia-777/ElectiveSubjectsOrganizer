package org.agh.electer.core.controllers;


import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.agh.electer.core.dto.AdminCredentialsDTO;
import org.agh.electer.core.infrastructure.dao.AdminDao;
import org.agh.electer.core.infrastructure.entities.AdminEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class AdminController {
    private AdminDao adminDao;

    @Autowired
    public AdminController(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    @PostMapping("/admin/login")
    public boolean login(@RequestBody AdminCredentialsDTO credentialsDTO) {
        val result = adminDao.findById(credentialsDTO.getLogin())
                .filter(s -> checkIfCorrectAdminLogin(credentialsDTO, s))
                .isPresent();
        log.info("Czy admin sie zalogował: " + result);
        return result;
    }

    private boolean checkIfCorrectAdminLogin(final AdminCredentialsDTO credentialsDTO, final AdminEntity adminEntity) {
        return credentialsDTO.getPassword().equals(adminEntity.getPassword());
    }
}
