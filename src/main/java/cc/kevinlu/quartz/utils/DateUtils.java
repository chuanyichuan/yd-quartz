package cc.kevinlu.quartz.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.SystemUtils;

import cc.kevinlu.quartz.common.exception.ParamErrorException;
import cc.kevinlu.quartz.error.CommonError;
import lombok.extern.slf4j.Slf4j;

/**
 * 日期工具类
 *
 * @author cc
 */
@Slf4j
public class DateUtils {
    /**
     * 默认时间字符串的格式
     */
    public static final String             DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter DEFAULT_FORMATTER               = DateTimeFormatter
            .ofPattern(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);

    /**
     * string2date
     * 
     * @param date 格式：yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date strToDate(String date) throws ParseException {
        if (SystemUtils.IS_JAVA_1_8 || SystemUtils.IS_JAVA_9 || SystemUtils.IS_JAVA_10 || SystemUtils.IS_JAVA_11) {
            LocalDateTime localDateTime = LocalDateTime.parse(date, DEFAULT_FORMATTER);
            Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
            return Date.from(instant);
        }
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        return df.parse(date);
    }

    /**
     * date2string
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        DateFormat df = new SimpleDateFormat(DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        return df.format(date);
    }

    public static boolean isBefore(String date1, String date2) {
        try {
            return isBefore(strToDate(date1), strToDate(date2));
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new ParamErrorException(CommonError.DATE_ERROR_ERROR);
        }
    }

    public static boolean isBefore(String date) {
        try {
            return isBefore(strToDate(date), new Date());
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new ParamErrorException(CommonError.DATE_ERROR_ERROR);
        }
    }

    public static boolean isBefore(String date1, Date date2) {
        try {
            return isBefore(strToDate(date1), date2);
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
            throw new ParamErrorException(CommonError.DATE_ERROR_ERROR);
        }
    }

    public static boolean isBefore(Date date) {
        return isBefore(date, new Date());
    }

    public static boolean isBefore(Date date1, Date date2) {
        return date1.before(date2);
    }

}
