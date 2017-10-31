function maxSizePagination(value) {
	var maxSize = 0;
	value = parseInt(value);
	
	if(value < 9){
		maxSize = 1;

	}else if(value > 50){
		maxSize = 5;

	}else{
		maxSize = Math.ceil(value / 10);
	}
	return maxSize;

}