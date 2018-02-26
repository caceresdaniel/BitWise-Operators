SCRAPPED CODE - MIGHT NEED LATER

	for (int index = 0; index < message.length(); index++) {
			for (int i = 0; i < message.length() * 8; i++) {
				if (i % 8 == 0 && count != 8) {
					char mask = (char) (1 << (count - 1));
					if ((message.charAt(index) & mask) == 0) {

					}
					count = 8;
				} else {

					count--;
				}
			}
		}
		
		
MORE SCRAPPED CODE

public String recoverMessage() {
		char[] bits = getPixelData();

		char mask = (char) 1 << 0;

		int count = 0;
		
		String message = null;
		
		ArrayList<Integer> onesAndZeros = new ArrayList<>();

		for (int i = 0; i < bits.length; i++) {
			if (count == 8) {
				break;
			} else if ((bits[i] & mask) == 0) {
				onesAndZeros.add(0);
				count++;
			} else {
				onesAndZeros.add(1);
				count = 0;
			}
		}
		
	//	message = rmHelper(onesAndZeros);
		
		System.out.println(onesAndZeros.toString());
		
		
		return message;
	}
	
	private String rmHelper(ArrayList<Integer> onesAndZeros){
		
		String message = null;
		
		int count = 8;
		
		for(int i = 0; i < onesAndZeros.size(); i++){
			if(onesAndZeros.get(i % 8 - count).equals(1)){
				int num = (int) Math.pow(2, (i % 8) + count - i);	
			}
			
		}
		
		
		return message;
	}
