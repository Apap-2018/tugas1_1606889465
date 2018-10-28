package com.apap.tugas1.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="instansi")
public class InstansiModel implements Serializable {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Size(max=20)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name="nama", nullable=false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name="deskripsi", nullable=false)
	private String deskripsi;
	
	@OneToMany(mappedBy = "idInstansi", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PegawaiModel> pegawai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_provinsi", referencedColumnName="id", nullable=false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private ProvinsiModel idProvinsi;



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public String getNama() {
		return nama;
	}



	public void setNama(String nama) {
		this.nama = nama;
	}



	public String getDeskripsi() {
		return deskripsi;
	}



	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}



	public ProvinsiModel getIdProvinsi() {
		return idProvinsi;
	}



	public void setIdProvinsi(ProvinsiModel idProvinsi) {
		this.idProvinsi = idProvinsi;
	}



	public List<PegawaiModel> getPegawai() {
		return pegawai;
	}



	public void setPegawai(List<PegawaiModel> pegawai) {
		this.pegawai = pegawai;
	} 
	
	
	// FOREIGN KEY KE PROVINSI.ID
	

}
