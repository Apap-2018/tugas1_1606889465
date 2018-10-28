package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {

	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;
	
	@RequestMapping(path = "/jabatans", method = RequestMethod.GET)
	public @ResponseBody List<JabatanModel> getAllJabatan() {
		return jabatanService.getListJabatan();
	}

	@RequestMapping("jabatan/viewall")
	public String viewall(Model model) {
		return "jabatan-viewall";

	}

	@RequestMapping(value = "jabatan/view", method = RequestMethod.GET)
	public String viewJabatan(@RequestParam(value = "idJabatan", required = false) long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);	
		
		List<JabatanPegawaiModel> tempList = jabatanPegawaiService.getJabatanPegawaiByJabatan(jabatan); 
		double gajiPegawai = jabatan.getGajiPokok();
		long gaji = (long) gajiPegawai;

		model.addAttribute("jumlahPegawai", tempList.size());
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("gaji",gaji);

		return "detailJabatan";
	}

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		
		return "addJabatan";
	}

	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("message", "Success");
		return "addJabatan";
	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String updateJabatan(@RequestParam(value = "idJabatan", required =false) String id, Model model) {
		JabatanModel archive = jabatanService.getJabatanDetailById(Long.parseLong(id));
		model.addAttribute("jabatan", archive);
		
		return "ubahJabatan";
	}

	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String updateJabatanSubmit(@ModelAttribute JabatanModel newJabatan, Model model) {
		jabatanService.updateJabatan(newJabatan, newJabatan.getId());
		model.addAttribute("message", "Success");
		model.addAttribute("jabatan", newJabatan);
		return "ubahJabatan";
	}
	
	@RequestMapping(value="/jabatan/hapus", method=RequestMethod.POST)
	private String deleteJabatan(@RequestParam(value="id") long id, Model model){	
		JabatanModel jabatan = jabatanService.getJabatanDetailById(id);
		if(jabatan.getJabatan().isEmpty()) {
			jabatanService.deleteJabatan(jabatan);
			
			return "delete";
		}
		
		else {
			return "tolakDelete";
		}
		
	}
	

}