package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.repository.JabatanPegawaiDB;

@Service
@Transactional
public class JabatanPegawaiServiceimpl implements JabatanPegawaiService {
	@Autowired
	private JabatanPegawaiDB jabatanPegawaiDb;
	
	public JabatanPegawaiModel getJabatanPegawaiDetailById(long id) {
		return jabatanPegawaiDb.findById(id).get();
	}
	
	public List getJabatanPegawaiByJabatan(JabatanModel jabatan) {
		return jabatanPegawaiDb.findByJabatan(jabatan);
	}
	
	@Override
	public void add(JabatanPegawaiModel jabatanPegawai) {
		jabatanPegawaiDb.save(jabatanPegawai);
	}

}
