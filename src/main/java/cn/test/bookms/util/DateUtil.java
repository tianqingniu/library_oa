package cn.test.bookms.util;

public class DateUtil {
	/**
     * 计算两个日期相减
     * 
     * @param date1
     * @param date2
     * @return 返回天数
     */
    public static int getDay(java.util.Date date1, java.util.Date date2) {
        Long quot = date2.getTime() - date1.getTime();
        quot = quot / (DateUtilCommonConstants.NumeralConstant.ONE_THOUSAND
                * DateUtilCommonConstants.NumeralConstant.SIXTY * DateUtilCommonConstants.NumeralConstant.SIXTY
                * DateUtilCommonConstants.NumeralConstant.TWENTY_FOUR) + 1;
        return quot.intValue();
    }
}
