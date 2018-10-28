package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	List<PegawaiModel> getPegawaiDetailByInstansi(InstansiModel instansi);
	List<PegawaiModel> findPegawaiByInstansiAndJabatan(InstansiModel instansi, JabatanModel jabatan);
	
	Integer hitungGaji(String nip);
	
	List getJabatan(String nip);
	List getOrderPegawai(InstansiModel instansi);
	void addNip(PegawaiModel pegawai);
	void addPegawai(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel newPegawai, PegawaiModel oldPegawai);
}
