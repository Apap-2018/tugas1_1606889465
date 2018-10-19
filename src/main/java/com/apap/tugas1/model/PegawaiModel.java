package com.apap.tugas1.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;
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
@Table(name="pegawai")
public class PegawaiModel implements Serializable{
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nip", nullable=false, unique=true)
	private String nip;
	
	@NotNull
	@Size(max = 255)
	@Column(name="nama", nullable=false)
	private String nama;
	
	@NotNull
	@Size(max = 255)
	@Column(name="tempat_lahir", nullable=false)
	private String tempatLahir;
	
	@NotNull
	@Column(name="tanggal_lahir")
	private Date tanggalLahir;
	
	@NotNull
	@Size(max = 255)
	@Column(name="tahun_masuk", nullable=false)
	private String tahunMasuk;
	
	//
	// >>>>>>> ID_INSTANSI BELUM <<<<<<
	//
	@OneToMany(mappedBy = "pegawai", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<JabatanPegawaiModel> jabatanPegawai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_instansi", referencedColumnName="id" , nullable=false)
	@OnDelete(action = OnDeleteAction.NO_ACTION)
	@JsonIgnore
	private InstansiModel idInstansi;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Date getTanggalLahir() {
		return tanggalLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public String getTahunMasuk() {
		return tahunMasuk;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public InstansiModel getIdInstansi() {
		return idInstansi;
	}

	public void setIdInstansi(InstansiModel idInstansi) {
		this.idInstansi = idInstansi;
	}

	public String getTempatLahir() {
		return tempatLahir;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public List<JabatanPegawaiModel> getJabatanPegawai() {
		return jabatanPegawai;
	}

	public void setJabatanPegawai(List<JabatanPegawaiModel> jabatanPegawai) {
		this.jabatanPegawai = jabatanPegawai;
	}


	
	//ganti instansi model	
	
	
}
