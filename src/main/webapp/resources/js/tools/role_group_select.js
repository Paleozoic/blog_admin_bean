var roleGroupSelect = (function(){
	var select = {};
	/**
	 * roleGroupId 根机构ID 必填
	 * $click 绑定点击弹窗的页面元素，例如 $click = $("input[name='test']",dlg);
	 */
	select.singleSelect = function(roleGroupId,$click,ftn){
		$click.click(function(){
			var options = {
					id:'dlgRoleGroupSingleSelect',
					title:'机构选择',
					width:250,
					height:450,
					mask:true,
					url:"tools/role_group/single_select",
					data:{"roleGroupId":roleGroupId},
					onSubmit:ftn,
					beforeSubmit:setRoleGroup,
					clear:setRoleGroupEmpty
				};
			$(this).dialog(options);
		});
		
		var setRoleGroup = function(){
			var dlg = $.CurrentDialog;
			var roleGroupId = $("input[name='roleGroupId']",dlg).val();
			var roleGroupName = $("span[name='roleGroupName']",dlg).html();
			return {"roleGroupId":roleGroupId,"roleGroupName":roleGroupName};
		}
		var setRoleGroupEmpty = function(){
			return {"roleGroupId":"","roleGroupName":""};
		}
	}
	return select;
})();