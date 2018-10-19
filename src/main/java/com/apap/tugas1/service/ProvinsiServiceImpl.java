package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
	@Autowired
	ProvinsiDB provinsiDb;

	public ProvinsiModel getProvinsiDetailById(long id) {
		return provinsiDb.findById(id).get();
	}

	public List getListAllProvinsi() {
		List<ProvinsiModel> provinsiList = provinsiDb.findAll();
		return provinsiList;
	}
}
