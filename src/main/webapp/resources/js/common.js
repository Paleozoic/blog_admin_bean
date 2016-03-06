var Common=(function(){
	var common={};
	common.isEmpty = function(obj){
		if(obj==undefined||obj==null||obj=="") return true;
		return false;
	}
	return common;
})();