package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDB;
import com.apap.tugas1.repository.JabatanPegawaiDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	JabatanDB jabatanDb;



	public JabatanModel getJabatanDetailById(long id) {
		return jabatanDb.findById(id).get();
	}

	public List getListJabatan() {
		List<JabatanModel> jabatanList = jabatanDb.findAll();
		return jabatanList;

	}
	
	public List getListAllJabatan() {
		List<JabatanModel> listJabatan = jabatanDb.findAll();
		return listJabatan;
	}
	
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}

	public void updateJabatan(JabatanModel newJabatan, long id) {
		JabatanModel oldJabatan = this.getJabatanDetailById(id);

		oldJabatan.setNama(newJabatan.getNama());
		oldJabatan.setDeskripsi(newJabatan.getDeskripsi());
		oldJabatan.setGajiPokok(newJabatan.getGajiPokok());

	}

	public void deleteJabatan(JabatanModel jabatan) {

		jabatanDb.delete(jabatan);
	}
	
	
}
