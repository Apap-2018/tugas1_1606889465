package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService{
	@Autowired
	private InstansiDB instansiDb;
	
	public InstansiModel getInstansiDetailById(long id) {
		return instansiDb.findById(id).get();
	}
	
	public List getListInstansi() {
		List<InstansiModel> instansiList = instansiDb.findAll();
		return instansiList;
	}
	
	public List<InstansiModel> getInstansiByIdProvinsi(ProvinsiModel provinsi){
		List<InstansiModel> instansiList= instansiDb.findInstansiByIdProvinsi(provinsi);
		return instansiList;
	}
	

}
