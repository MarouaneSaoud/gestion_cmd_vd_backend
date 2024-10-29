package com.veri_delice.gestion_cmd_vd_backend.restController;
import com.veri_delice.gestion_cmd_vd_backend.constant.path.UserPath;
import com.veri_delice.gestion_cmd_vd_backend.dto.auth.UserStatistics;
import com.veri_delice.gestion_cmd_vd_backend.dto.client.ClientDto;
import com.veri_delice.gestion_cmd_vd_backend.dto.product.ProductDTO;
import com.veri_delice.gestion_cmd_vd_backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping(UserPath.BASE_URL)
public class UserController {
    private final UserService userService;
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(UserPath.USER_PRODUCTS)
    public ResponseEntity<Set<ProductDTO>> userProduct(@PathVariable String email) {
        return new ResponseEntity<>(userService.userProduct(email), HttpStatus.OK);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(UserPath.USER_CLIENTS)
    public ResponseEntity<Set<ClientDto>> userClient(@PathVariable String email) {
        return new ResponseEntity<>(userService.userClient(email), HttpStatus.OK);
    }
    @PreAuthorize( "hasRole('ADMIN')" )
    @GetMapping(UserPath.USER_STATISTICS)
    public ResponseEntity<UserStatistics> userStatistics(@PathVariable String email) {
        return new ResponseEntity<>(userService.userStatistics(email), HttpStatus.OK);
    }
}
