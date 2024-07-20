package back.example.project.dto;

import lombok.Data;


@Data
public class MemberDto {

    private String name;

    private String surname;

    private String profession;

    private Boolean activity;
}
