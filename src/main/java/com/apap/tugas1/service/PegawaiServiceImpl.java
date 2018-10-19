package com.apap.tugas1.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.JabatanPegawaiDB;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDb;

	@Autowired
	private JabatanPegawaiDB jabatanPegawaiDb;

	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}
	
	
	
	@Override
	public void addPegawai(PegawaiModel pegawai) {
		
		
		String nip = "";
		nip += pegawai.getIdInstansi().getId();

		Date tanggalLahir = pegawai.getTanggalLahir();
		String[] tglLahir = (""+tanggalLahir).split("-");
		for (int i = tglLahir.length-1; i >= 0; i--) {
			int ukuranTgl = tglLahir[i].length();
			nip += tglLahir[i].substring(ukuranTgl-2, ukuranTgl);
		}
		
		nip += pegawai.getTahunMasuk();
		
		List<PegawaiModel> pegawaiList = pegawaiDb.findByTanggalLahirAndTahunMasukAndIdInstansi(pegawai.getTanggalLahir(), pegawai.getTahunMasuk(), pegawai.getIdInstansi());
		
		int totalPegawai = pegawaiList.size();
		
		if (totalPegawai >= 10) {
			nip += totalPegawai;
		}
		else {
			nip += "0" + (totalPegawai+1);
		}
		
		pegawai.setNip(nip);
		System.out.println(nip);
		pegawaiDb.save(pegawai);
	}

	@Override
	public Integer hitungGaji(String nip) {
		PegawaiModel pegawai = pegawaiDb.findByNip(nip);
		List<JabatanPegawaiModel> tempList = pegawai.getJabatanPegawai();
		List<Double> seluruhGaji = new ArrayList<Double>();
		Double gajiTertinggi = 0.0;

		for (JabatanPegawaiModel jabatan : tempList) {
			Double gaji = jabatan.getJabatan().getGajiPokok();
			if (gaji > gajiTertinggi) {
				gajiTertinggi = gaji;
			}
		}

		Double presentaseTunjangan = pegawai.getIdInstansi().getIdProvinsi().getPresentaseTunjangan();
		Double totalGajid = gajiTertinggi + (gajiTertinggi * presentaseTunjangan / 100);

		Integer totalGaji = totalGajid.intValue();
		return totalGaji;

	}

	@Override
	public List getJabatan(String nip) {
		PegawaiModel pegawai = pegawaiDb.findByNip(nip);
		List<JabatanPegawaiModel> tempList = pegawai.getJabatanPegawai();
		List<String> jenisJabatan = new ArrayList<String>();
		for (JabatanPegawaiModel jabatan : tempList) {
			jenisJabatan.add(jabatan.getJabatan().getNama());
		}

		return jenisJabatan;
	}
	
	
	
	@Override
	public List getOrderPegawai(InstansiModel instansi) {
		return pegawaiDb.findByIdInstansiOrderByTanggalLahirAsc(instansi);
	}
	
	

}
