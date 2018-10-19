package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;

public interface JabatanPegawaiService {
	JabatanPegawaiModel getJabatanPegawaiDetailById(long id);
	List getJabatanPegawaiByJabatan(JabatanModel jabatan);
	void add(JabatanPegawaiModel jabatanPegawai);
}
