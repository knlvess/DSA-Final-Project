public class AppointmentMap {
    AppointmentManagement[] appointment;
	
	public AppointmentMap(int capacity){
		this.appointment = new AppointmentManagement[capacity];
        for(int i = 0; i < appointment.length; i++){
            this.appointment[i] = new AppointmentManagement(6);
        }
	}

	public void put(int key, AppointmentManagement value){
        appointment[key] = value;
	}

	public AppointmentManagement get(int key){ 
		return appointment[key];
	}

	public void remove(int key){ 
		appointment[key] = null;
	}
    
}
