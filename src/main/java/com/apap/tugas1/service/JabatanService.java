package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	JabatanModel getJabatanDetailById(long id);
	void addJabatan(JabatanModel jabatan);
	void updateJabatan(JabatanModel jabatan, long id);
	void deleteJabatan(JabatanModel jabatan);
	List getListJabatan();
	List getListAllJabatan();
	
	
	
	
}
