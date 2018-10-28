$(document).ready( function () {
	 var table = $('#jabatanTable').DataTable({
		
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    
		      { "mData": "nip" },
				  { "mData": "nama" },
				  { "mData": "tempatLahir" },
				  { "mData": "tanggalLahir" },
				  { "mData": "tahunMasuk" },
				  { "mData": "namaInstansi" },
				  { "mData": "namaJabatan" },
			]
	 })
});