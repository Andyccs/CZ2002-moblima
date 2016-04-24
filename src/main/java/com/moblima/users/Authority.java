package com.moblima.users;

public enum Authority
{
	Public{
		public String toString()
		{
			return "Public";
		}
	}, 
	Customer{
		public String toString()
		{
			return "Customer";
		}
	},
	Staff{
		public String toString()
		{
			return "Staff";
		}
	};
}