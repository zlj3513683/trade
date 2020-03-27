package com.posppay.newpay.modules.xposp.utils;


import com.posppay.newpay.modules.xposp.common.Const;

/**
 * Created by VJ on 2018/2/2.
 */
public class BankCardUtils {
	/**
	 * 获取60.5域值
	 * @param enterMode  22域输入点条件码
	 * @param track2  二磁道信息
	 * @return 0-正常交易标志 2-芯片卡降级交易标志
	 */
		public static String getF60_5(String enterMode, String track2){
			if(StringUtils.isBlank(enterMode) || enterMode.length() != 3) {
				return Const.SD60_5_IC_SERVICE_CODE_NORMAL;
			}
			String _mode = enterMode.substring(0, 2);
			boolean isChipCard = isChipCard(track2);
			//如果enterMode前2位是02或90，且从磁道信息判断该卡为'2''6'芯片卡，需要设置60.5域为2，表示是降级交易
			if((_mode.equals("02") || _mode.equals("90")) && isChipCard){
				return "2";
			}
				return Const.SD60_5_IC_SERVICE_CODE_NORMAL;
		}

	/**
	 * 判断银行卡是否芯片卡
	 * @return 是否芯片卡
	 */
	public static boolean isChipCard(String track2){
		if(null == track2 || track2.length() <= 0) {
			return false;
		}
		int seperator = track2.indexOf('=');
		if(seperator <=0) {
			seperator = track2.indexOf('D');
		}
		if(seperator <=0) {
			seperator = track2.indexOf('d');
		}

		if(seperator >=0){
			if(track2.getBytes()[seperator+5] == '2' || track2.getBytes()[seperator+5] == '6'){
				return true;
			}
			return false;
		}else {
			return false;
		}
	}

	/**
	 * 获取卡号
	 * @param track2 二磁道信息
	 * @param track3 三磁道信息
	 * @return 卡号
	 */
	public static String getCardNo(String track2, String track3) {
		if (null != track2 && track2.length() > 0) {
			int idx = track2.indexOf('=');
			if (idx <= 0) {
				idx = track2.indexOf('D');
			}
			if (idx <= 0) {
				idx = track2.indexOf('d');
			}
			if (idx < 13 || idx > 19) {
				return null;
			}
			return track2.substring(0, idx);
		} else if (null != track3 && track3.length() > 0) {
			int idx = track3.indexOf('=');
			if (idx <= 0) {
				idx = track3.indexOf('D');
			}
			if (idx <= 0) {
				idx = track2.indexOf('d');
			}
			if (idx < 15 || idx > 21) {
				return null;
			}
			return track3.substring(2, idx);
		}
		return null;

	}

	/**
     * 获取卡号
     * @param track2 二磁道信息
     * @param track3 三磁道信息
     * @return 卡号
     */
    public static String getCardNo(String track2, String track3, String cardNo) {
        if (null != track2 && track2.length() > 0) {
            int idx = track2.indexOf('=');
            if (idx <= 0) {
                idx = track2.indexOf('D');
            }
            if (idx <= 0) {
                idx = track2.indexOf('d');
            }

            return track2.substring(0, idx);
        } else if (null != track3 && track3.length() > 0) {
            int idx = track3.indexOf('=');
            if (idx <= 0) {
                idx = track3.indexOf('D');
            }
            if (idx <= 0) {
                idx = track2.indexOf('d');
            }
            if (idx < 15 || idx > 21) {
                return null;
            }
            return track3.substring(2, idx);
        } else if (null != cardNo && cardNo.length() > 0) {
        	return cardNo;
        }
        return null;

    }


}
