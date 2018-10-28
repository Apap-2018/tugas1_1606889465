package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.JabatanPegawaiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanPegawaiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;

	@Autowired
	private InstansiService instansiService;

	@Autowired
	private JabatanService jabatanService;

	@Autowired
	private ProvinsiService provinsiService;

	@Autowired
	private JabatanPegawaiService jabatanPegawaiService;

	@RequestMapping(value = "/")
	private String home(Model model) {
		List<String> listJabatan = jabatanService.getListJabatan();
		List<String> listInstansi = instansiService.getListInstansi();

		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("status", "nav-item active");
		return "home";
	}

	@RequestMapping("/pegawai")
	public String view(@RequestParam(value = "nip", required = false) String nip, Model model) {
		PegawaiModel archivePegawai = pegawaiService.getPegawaiDetailByNip(nip);
		InstansiModel archiveInstansi = archivePegawai.getIdInstansi();
		ProvinsiModel archiveProvinsi = archiveInstansi.getIdProvinsi();
		String temporary = archiveInstansi.getNama() + "-" + archiveProvinsi.getNama();
		List<String> jabatan = pegawaiService.getJabatan(nip);

		model.addAttribute("gaji", pegawaiService.hitungGaji(nip));
		model.addAttribute("pegawai", archivePegawai);
		model.addAttribute("tulisan", temporary);
		model.addAttribute("jabatan", jabatan);
		return "view-pegawai";
	}

	@RequestMapping(value = "pegawai/termuda-tertua", method = RequestMethod.GET)
	public String viewTermudaTertua(@RequestParam(value = "idInstansi", required = false) String id, Model model) {
		InstansiModel instansi = instansiService.getInstansiDetailById(Long.parseLong(id));

		List<PegawaiModel> orderPegawai = pegawaiService.getOrderPegawai(instansi);
		PegawaiModel termuda = orderPegawai.get(orderPegawai.size() - 1);
		PegawaiModel tertua = orderPegawai.get(0);

		List<JabatanPegawaiModel> jabatanPegawaiMuda = termuda.getJabatanPegawai();
		List<JabatanPegawaiModel> jabatanPegawaiTertua = tertua.getJabatanPegawai();

		model.addAttribute("termuda", termuda);
		model.addAttribute("tertua", tertua);

		model.addAttribute("jabatanPegawaiMuda", jabatanPegawaiMuda);
		model.addAttribute("jabatanPegawaiTertua", jabatanPegawaiTertua);

		return "termudatertua";
	}

	@RequestMapping(value = "pegawai/tambah", method = RequestMethod.GET)
	public String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		List<ProvinsiModel> listProvinsi = provinsiService.getListAllProvinsi();
		List<JabatanPegawaiModel> jabatanPegawai = new ArrayList<JabatanPegawaiModel>();

		pegawai.setJabatanPegawai(jabatanPegawai);

		JabatanPegawaiModel pegawaiJabatan = new JabatanPegawaiModel();
		pegawaiJabatan.setPegawai(pegawai);

		pegawai.getJabatanPegawai().add(pegawaiJabatan);

		List<JabatanModel> listJabatan = jabatanService.getListJabatan();
		List<InstansiModel> listInstansi = instansiService.getListInstansi();

		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);

		return "addPegawai";

	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {

		List<JabatanPegawaiModel> listJabatan = pegawai.getJabatanPegawai();
		pegawai.setJabatanPegawai(new ArrayList<JabatanPegawaiModel>());

		pegawaiService.addNip(pegawai);
		pegawaiService.addPegawai(pegawai);

		// insert all jabatan
		for (int i = 0; i < listJabatan.size(); i++) {
			listJabatan.get(i).setPegawai(pegawai);
			jabatanPegawaiService.add(listJabatan.get(i));
		}

		model.addAttribute("nip", pegawai.getNip());
		return "addPegawaiSucces";
	}

	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params = { "addJabatan" })
	private String addRow(@ModelAttribute PegawaiModel pegawaiBaru, Model model) {
		PegawaiModel newPegawai = pegawaiBaru;

		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(newPegawai);
		newPegawai.getJabatanPegawai().add(jabatanPegawai);

		List<InstansiModel> listInstansi = instansiService.getListInstansi();
		List<JabatanModel> listJabatan = jabatanService.getListAllJabatan();
		List<ProvinsiModel> listProvinsi = provinsiService.getListAllProvinsi();

		model.addAttribute("pegawai", newPegawai);
		model.addAttribute("listInstansi", listInstansi);
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listProvinsi", listProvinsi);

		return "addPegawai";
	}

	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	public String cariPegawai(Model model) {

		List<ProvinsiModel> daftarProvinsi = provinsiService.getListAllProvinsi();

		List<InstansiModel> daftarInstansi = new ArrayList<InstansiModel>();
		daftarInstansi = daftarProvinsi.get(0).getInstansi();
		List<JabatanModel> daftarJabatan = jabatanService.getListAllJabatan();

		model.addAttribute("daftarProvinsi", daftarProvinsi);
		model.addAttribute("daftarInstansi", daftarInstansi);
		model.addAttribute("daftarJabatan", daftarJabatan);

		return "caripegawai";
	}

	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET, params = { "search" })
	public String cariPegawaiBaru(@RequestParam(value = "IdProvinsi", required = false) Optional<Long> IdProvinsi,
			@RequestParam(value = "IdInstansi", required = false) Optional<Long> IdInstansi,
			@RequestParam(value = "IdJabatan", required = false) Optional<Long> IdJabatan, Model model) {

		List<PegawaiModel> daftarPegawai = new ArrayList<PegawaiModel>();

		if (IdProvinsi.isPresent()) {

			if (IdInstansi.isPresent()) {

				/** Jika Provinsi ,instansi, dan jabatan di isi */
				if (IdJabatan.isPresent()) {
					daftarPegawai = pegawaiService.findPegawaiByInstansiAndJabatan(
							instansiService.getInstansiDetailById(IdInstansi.get()),
							jabatanService.getJabatanDetailById(IdJabatan.get()));
				}

				/** Jika hanya provinsi dan instansi yang di isi */
				else {
					daftarPegawai = pegawaiService
							.getPegawaiDetailByInstansi(instansiService.getInstansiDetailById(IdInstansi.get()));
				}

			}

			else {

				/** Jika Provinsi dan jabatan di isi */
				if (IdJabatan.isPresent()) {
					List<InstansiModel> daftarInstansi = provinsiService.getProvinsiDetailById(IdProvinsi.get())
							.getInstansi();
					for (InstansiModel cariInstansi : daftarInstansi) {
						List<PegawaiModel> daftarPegawaiBaru = cariInstansi.getPegawai();
						for (PegawaiModel cariPegawai : daftarPegawaiBaru) {
							for (JabatanPegawaiModel cariPegawaiBaru : cariPegawai.getJabatanPegawai()) {
								if (cariPegawaiBaru.getJabatan().getId() == IdJabatan.get()) {
									daftarPegawai.add(cariPegawai);
									break;
								}
							}
						}
					}

				}

				/** Jika Hanya Provinsi yang di isi */
				else {
					List<InstansiModel> daftarInstansi = provinsiService.getProvinsiDetailById(IdProvinsi.get())
							.getInstansi();
					for (InstansiModel cariInstansi : daftarInstansi) {
						List<PegawaiModel> daftarPegawaiBaru = cariInstansi.getPegawai();
						daftarPegawai.addAll(daftarPegawaiBaru);
					}

				}
			}
		}

		else {
			/** Jika hanya jabatan yang di isi */
			if (IdJabatan.isPresent()) {
				JabatanModel jabatan = jabatanService.getJabatanDetailById(IdJabatan.get());
				for (JabatanPegawaiModel jabatans : jabatan.getJabatan()) {
					daftarPegawai.add(jabatans.getPegawai());
				}

			}
		}

		List<ProvinsiModel> daftarProvinsi = provinsiService.getListAllProvinsi();

		List<InstansiModel> daftarInstansi = new ArrayList<InstansiModel>();
		List<JabatanModel> daftarJabatan = jabatanService.getListAllJabatan();

		model.addAttribute("daftarProvinsi", daftarProvinsi);
		model.addAttribute("daftarInstansi", daftarInstansi);
		model.addAttribute("daftarJabatan", daftarJabatan);
		model.addAttribute("daftarPegawai", daftarPegawai);
		return "caripegawai";
	}

	@RequestMapping(value = "pegawai/ubah", method = RequestMethod.GET)
	public String ubahPegawai(@RequestParam(value = "nip", required = false) String nip, Model model) {
		PegawaiModel archivePegawai = pegawaiService.getPegawaiDetailByNip(nip);

		List<JabatanPegawaiModel> archiveJabatanPegawai = archivePegawai.getJabatanPegawai();
		List<JabatanModel> jabatan = new ArrayList<JabatanModel>();
		for (JabatanPegawaiModel jabatanPegawai : archiveJabatanPegawai) {
			jabatan.add(jabatanPegawai.getJabatan());
		}

		List<ProvinsiModel> daftarProvinsi = provinsiService.getListAllProvinsi();
		List<InstansiModel> daftarInstansi = instansiService.getListInstansi();
		List<JabatanModel> daftarJabatan = jabatanService.getListAllJabatan();

		model.addAttribute("pegawai", archivePegawai);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("daftarProvinsi", daftarProvinsi);
		model.addAttribute("daftarInstansi", daftarInstansi);
		model.addAttribute("daftarJabatan", daftarJabatan);

		return "updatePegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST)
	public String ubahPegawaiSubmit(@ModelAttribute PegawaiModel newPegawai, Model model) {
		
		PegawaiModel oldPegawai = pegawaiService.getPegawaiDetailByNip(newPegawai.getNip());

		if (oldPegawai.getIdInstansi()==newPegawai.getIdInstansi()&&oldPegawai.getTahunMasuk().equals(newPegawai.getTahunMasuk())&&oldPegawai.getTanggalLahir().equals(newPegawai.getTanggalLahir())) {
			pegawaiService.updatePegawai(newPegawai, oldPegawai);
		}

		else {
			pegawaiService.addNip(newPegawai);
			pegawaiService.updatePegawai(newPegawai, oldPegawai);
		}
		model.addAttribute("nip", oldPegawai.getNip());
		return "updatePegawaiSuccess";
	}

	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params = { "addJabatan" })
	public String addRowUbah(@ModelAttribute PegawaiModel pegawaiBaru, Model model) {
		PegawaiModel pegawai = pegawaiBaru;

		JabatanPegawaiModel jabatanPegawai = new JabatanPegawaiModel();
		jabatanPegawai.setPegawai(pegawai);
		pegawai.getJabatanPegawai().add(jabatanPegawai);

		List<ProvinsiModel> daftarProvinsi = provinsiService.getListAllProvinsi();
		List<InstansiModel> daftarInstansi = new ArrayList<InstansiModel>();
		List<JabatanModel> daftarJabatan = jabatanService.getListAllJabatan();

		model.addAttribute("daftarProvinsi", daftarProvinsi);
		model.addAttribute("daftarInstansi", daftarInstansi);
		model.addAttribute("daftarJabatan", daftarJabatan);
		model.addAttribute("pegawai", pegawai);
		return "updatePegawai";
	}


}
