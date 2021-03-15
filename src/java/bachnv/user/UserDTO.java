/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bachnv.user;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author ngvba
 */
public class UserDTO implements Serializable{
    private String email;
    private String password;
    private String phone;
    private String fullname;
    private String address;
    private Date createDate;
    private String status;
    private String role;

    public UserDTO(String email, String password, String phone, String fullname, String address, Date createDate, String status, String role) {
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.fullname = fullname;
        this.address = address;
        this.createDate = createDate;
        this.status = status;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
}
