package com.deepika.adpinterview.coinmachine.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COIN_CT")
public class CoinCount {

	public CoinCount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CoinCount(int coin_val, int avl_ct) {
		super();
		this.coinVal = coin_val;
		this.avlCt = avl_ct;
	}

	@Id
	@Column(name = "COIN_VAL")
	private int coinVal;

	@Column(name = "AVL_CT", nullable = false)
	private int avlCt;

	public int getCoinVal() {
		return coinVal;
	}

	public void setCoinVal(int coinVal) {
		this.coinVal = coinVal;
	}

	public int getAvlCt() {
		return avlCt;
	}

	public void setAvlCt(int avlCt) {
		this.avlCt = avlCt;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		CoinCount cc = (CoinCount) o;

		if (coinVal != cc.coinVal)
			return false;
		if (avlCt != cc.avlCt)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + coinVal;
		result = 31 * result + avlCt;
		return result;
	}

}
