package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	
	
	
	Integer hitungGaji(String nip);
	
	List getJabatan(String nip);
	List getOrderPegawai(InstansiModel instansi);
	void addPegawai(PegawaiModel pegawai);
}
