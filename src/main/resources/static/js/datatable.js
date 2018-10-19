$(document).ready( function () {
	 var table = $('#jabatanTable').DataTable({
			"sAjaxSource": "/jabatans",
			"sAjaxDataProp": "",
			"order": [[ 0, "asc" ]],
			"aoColumns": [
			    
		      { "mData": "nama" },
				  { "mData": "deskripsi" },
				  { "mData": "gajiPokok" },
				 
			]
	 })
});

