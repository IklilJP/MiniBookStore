package model.dto.response;

import model.entity.Staffs;

public class StaffResponse {
    private String id;
    private String name;
    private String email;
    private Staffs.StaffPosition position;

    public StaffResponse(String id, String name, String email, Staffs.StaffPosition position) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.position = position;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Staffs.StaffPosition getPosition() {
        return position;
    }

    public void setPosition(Staffs.StaffPosition position) {
        this.position = position;
    }

    public static StaffResponse fromEntity(Staffs staff) {
        return new StaffResponse(
                staff.getId(),
                staff.getName(),
                staff.getEmail(),
                staff.getPosition()
        );
    }
}
