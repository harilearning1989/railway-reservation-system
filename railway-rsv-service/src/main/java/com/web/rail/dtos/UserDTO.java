package com.web.rail.dtos;

import java.util.List;

public record UserDTO(Long id, String username, List<RoleDTO> list) {
}
