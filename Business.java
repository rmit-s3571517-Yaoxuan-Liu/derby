package business;

import java.sql.Date;

/**
 * the class for reading csv
 */
public class Business {
	String register_name;
	String bn_name;
	String bn_status;
	String bn_reg_dt;
	String bn_cancel_dt;
	String bn_renew_dt;
	String bn_state_num;
	String bn_state_of_reg;
	String ibn_abn;
	
	public Business() {
		
	}
	
	public String toString() {
		return String.format(String.format("resister_name: %s, bn_name: %s, bn_status: %s, bn_reg_dt: %s, bn_cancel_dt: %s, bn_renew_dt: %s, "
				+ "bn_state_num: %s, bn_state_of_reg: %s, ibn_abn: %s", this.register_name, this.bn_name, this.bn_status, 
				this.bn_reg_dt, this.bn_cancel_dt, this.bn_renew_dt, this.bn_state_num, this.bn_state_of_reg, this.ibn_abn));
	}
}
