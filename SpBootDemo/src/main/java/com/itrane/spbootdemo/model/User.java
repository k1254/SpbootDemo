package com.itrane.spbootdemo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id = null;

	@Version
	protected Integer version = 0;

	// ログイン情報
	@Column(unique = true, length = 12)
	@Length(min = 4, max = 12, message = "ユーザー名は{min}から{max}文字の範囲で入力してください。")
	private String name;

	// 個人情報
	@Column(length = 30)
	private String simei;
	@Column(length = 30)
	private String yomi;
	@Column(length = 10)
	private String seinenGappi; // yyyy/MM/dd
	@Column(length = 2)
	private String seiBetu;
	@Column(length = 13)
	private String telNo;
	@Column(length = 8)
	private String yubinBango;
	@Column(length = 10)
	private String toDoFuKen;
	@Column(length = 20)
	private String siKuTyoSon;
	@Column(length = 40)
	private String banTi;
	@Column(length = 40)
	private String tatemono;
	@Column(length = 40)
	@Email
	private String email;

	public User() {
		//
	}

	public User(String name, String simei, String yomi, String seinenGappi,
			String seiBetu) {
		super();
		this.name = name;
		this.simei = simei;
		this.yomi = yomi;
		this.seinenGappi = seinenGappi;
		this.seiBetu = seiBetu;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSimei() {
		return simei;
	}

	public void setSimei(String simei) {
		this.simei = simei;
	}

	public String getYomi() {
		return yomi;
	}

	public void setYomi(String yomi) {
		this.yomi = yomi;
	}

	public String getSeinenGappi() {
		return seinenGappi;
	}

	public void setSeinenGappi(String seinenGappi) {
		this.seinenGappi = seinenGappi;
	}

	public String getSeiBetu() {
		return seiBetu;
	}

	public void setSeiBetu(String seiBetu) {
		this.seiBetu = seiBetu;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getYubinBango() {
		return yubinBango;
	}

	public void setYubinBango(String yubinBango) {
		this.yubinBango = yubinBango;
	}

	public String getToDoFuKen() {
		return toDoFuKen;
	}

	public void setToDoFuKen(String toDoFuKen) {
		this.toDoFuKen = toDoFuKen;
	}

	public String getSiKuTyoSon() {
		return siKuTyoSon;
	}

	public void setSiKuTyoSon(String siKuTyoSon) {
		this.siKuTyoSon = siKuTyoSon;
	}

	public String getBanTi() {
		return banTi;
	}

	public void setBanTi(String banTi) {
		this.banTi = banTi;
	}

	public String getTatemono() {
		return tatemono;
	}

	public void setTatemono(String tatemono) {
		this.tatemono = tatemono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", version=" + version + ", name=" + name
				+ ", simei=" + simei + ", yomi=" + yomi + ", seinenGappi="
				+ seinenGappi + ", seiBetu=" + seiBetu + ", telNo=" + telNo
				+ ", yubinBango=" + yubinBango + ", toDoFuKen=" + toDoFuKen
				+ ", siKuTyoSon=" + siKuTyoSon + ", banTi=" + banTi
				+ ", tatemono=" + tatemono + ", email=" + email + "]";
	}
}
