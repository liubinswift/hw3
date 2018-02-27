package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;

/**
 * 指标报警任务设置
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Viewscenes</p>
 *
 * @author 赵广磊
 * @version 8.0
 */

public class MsgQualityAlarmParamSetCmd extends BaseMessageCommand {

    private String equCode, band, freq;

    private String level_downThreshold, level_abnormityLength;

    private String fm_sampleLength, fm_downThreshold, fm_upThreshold, fm_upAbnormityRate, fm_downAbnormityRate, fm_abnormityLength;

    private String sampleLength, downThreshold, upThreshold, upAbnormityRate, downAbnormityRate, abnormityLength;

    private String attenuation;

    private String offset_upThreshold, offset_abnormityLength;

    boolean isAm = false;
    boolean isFm = false;

    public MsgQualityAlarmParamSetCmd() {

        setBuilders(
            new IMessageBuilder[] {
                    new BuilderV8Radio()
            }
        );
    }

    /**
     * v8、v7、v6、v5、v4
     * @param equcode String
     * @param freq String
     * @param band String
     */
    public void setParams(String equcode, String freq, String band) {

        this.equCode = equcode;

        this.freq = freq;

        this.band = band;

    }
    /**
     * v8、v7、v6、v5、v4
     * type=1
     * @param level_downThreshold String
     * @param level_abnormityLength String
     *
     * 注：兼容V4
     *
     */
    public void setLevelParam(String level_downThreshold, String level_abnormityLength) {

        this.level_downThreshold = level_downThreshold;

        this.level_abnormityLength = level_abnormityLength;

    }
    /**
     * v8、v7、v6、v5
     * type=2
     * @param fm_sampleLength String
     * @param fm_downThreshold String
     * @param fm_upThreshold String
     * @param fm_upAbnormityRate String
     * @param fm_downAbnormityRate String
     * @param fm_abnormityLength String
     */
    public void setFMModulationParam(String fm_sampleLength, String fm_downThreshold, String fm_upThreshold,
                         String fm_upAbnormityRate, String fm_downAbnormityRate, String fm_abnormityLength) {

        this.fm_sampleLength = fm_sampleLength;

        this.fm_downThreshold = fm_downThreshold;

        this.fm_upThreshold = fm_upThreshold;

        this.fm_upAbnormityRate = fm_upAbnormityRate;

        this.fm_downAbnormityRate = fm_downAbnormityRate;

        this.fm_abnormityLength = fm_abnormityLength;

        isFm = true;
    }

    /**
     * v8、v7、v6、v5、v4
     * type=3
     * @param sampleLength String
     * @param downThreshold String
     * @param upThreshold String
     * @param upAbnormityRate String
     * @param downAbnormityRate String
     * @param abnormityLength String
     *
     * 注：兼容V4
     *
     */
    public void setAMModulationParam(String sampleLength, String downThreshold, String upThreshold,
                         String upAbnormityRate, String downAbnormityRate, String abnormityLength) {

        this.sampleLength = sampleLength;

        this.downThreshold = downThreshold;

        this.upThreshold = upThreshold;

        this.upAbnormityRate = upAbnormityRate;

        this.downAbnormityRate = downAbnormityRate;

        this.abnormityLength = abnormityLength;

        isAm = true;
    }

    /**
     * v8、v7、v6、v5
     * type=4
     * @param attenuation String
     */
    public void setAttenuationParam(String attenuation) {

        this.attenuation = attenuation;

    }

    /**
     * v4
     * @param offset_upThreshold String
     * @param offset_abnormityLength String
     */
    public void setOffsetParam(String offset_upThreshold, String offset_abnormityLength) {

        this.offset_upThreshold = offset_upThreshold;

        this.offset_abnormityLength = offset_abnormityLength;

    }


    /**
     * V8版，具体实现
     */
    protected class BuilderV8Radio extends BaseMessageCommand.BuilderV8Radio {

        protected BuilderV8Radio() {
        }

        protected MessageElement buildBody() {

            Map attrs = new LinkedHashMap(3);
            attrs.put("EquCode", equCode);
            attrs.put("Band", band);
            attrs.put("Freq", freq);
            Collection alarmParams = new ArrayList();
            Map attr = new LinkedHashMap(2);
            attr.put("Type", "1");
            attr.put("Desc", "Level");
            Collection c = new ArrayList(2);
            Map pa1 = new HashMap(2);
            pa1.put("Name", "DownThreshold");
            pa1.put("Value", level_downThreshold);
            c.add(new MessageElement("Param", pa1, null));
            Map pa2 = new HashMap(2);
            pa2.put("Name", "AbnormityLength");
            pa2.put("Value", level_abnormityLength);
            c.add(new MessageElement("Param", pa2, null));
            alarmParams.add(new MessageElement("AlarmParam", attr, c));
            if (isFm) {
                attr = new LinkedHashMap(2);
                attr.put("Type", "2");
                attr.put("Desc", "FM-Modulation");
                c = new ArrayList(6);
                pa1 = new HashMap(2);
                pa1.put("Name", "SampleLength");
                pa1.put("Value", fm_sampleLength);
                c.add(new MessageElement("Param", pa1, null));
                pa2 = new HashMap(2);
                pa2.put("Name", "DownThreshold");
                pa2.put("Value", fm_downThreshold);
                c.add(new MessageElement("Param", pa2, null));
                Map pa3 = new HashMap(2);
                pa3.put("Name", "UpThreshold");
                pa3.put("Value", fm_upThreshold);
                c.add(new MessageElement("Param", pa3, null));
                Map pa4 = new HashMap(2);
                pa4.put("Name", "UpAbnormityRate");
                pa4.put("Value", fm_upAbnormityRate);
                c.add(new MessageElement("Param", pa4, null));
                Map pa5 = new HashMap(2);
                pa5.put("Name", "DownAbnormityRate");
                pa5.put("Value", fm_downAbnormityRate);
                c.add(new MessageElement("Param", pa5, null));
                Map pa6 = new HashMap(2);
                pa6.put("Name", "AbnormityLength");
                pa6.put("Value", fm_abnormityLength);
                c.add(new MessageElement("Param", pa6, null));
                alarmParams.add(new MessageElement("AlarmParam", attr, c));
            }
            if (isAm) {
                attr = new LinkedHashMap(2);
                attr.put("Type", "3");
                attr.put("Desc", "AM-Modulation");
                c = new ArrayList(6);
                pa1 = new HashMap(2);
                pa1.put("Name", "SampleLength");
                pa1.put("Value", sampleLength);
                c.add(new MessageElement("Param", pa1, null));
                pa2 = new HashMap(2);
                pa2.put("Name", "DownThreshold");
                pa2.put("Value", downThreshold);
                c.add(new MessageElement("Param", pa2, null));
                Map pa3 = new HashMap(2);
                pa3.put("Name", "UpThreshold");
                pa3.put("Value", upThreshold);
                c.add(new MessageElement("Param", pa3, null));
                Map pa4 = new HashMap(2);
                pa4.put("Name", "UpAbnormityRate");
                pa4.put("Value", upAbnormityRate);
                c.add(new MessageElement("Param", pa4, null));
                Map pa5 = new HashMap(2);
                pa5.put("Name", "DownAbnormityRate");
                pa5.put("Value", downAbnormityRate);
                c.add(new MessageElement("Param", pa5, null));
                Map pa6 = new HashMap(2);
                pa6.put("Name", "AbnormityLength");
                pa6.put("Value", abnormityLength);
                c.add(new MessageElement("Param", pa6, null));
                alarmParams.add(new MessageElement("AlarmParam", attr, c));
            }
            attr = new LinkedHashMap(2);
            attr.put("Type", "4");
            attr.put("Desc", "Attenuation");
            c = new ArrayList(1);
            pa1 = new HashMap(2);
            pa1.put("Name", "Attenuation");
            pa1.put("Value", attenuation);
            c.add(new MessageElement("Param", pa1, null));
            alarmParams.add(new MessageElement("AlarmParam", attr, c));
            Collection children = new ArrayList();
            children.add(new MessageElement("QualityAlarmParam", attrs, alarmParams));
            return new MessageElement("QualityAlarmParamSet", null, children);

        }
    }


    
    public static class FmParam {
    	private String fm_sampleLength;

    	private String fm_downThreshold ;

    	private String fm_upThreshold;
    	private String fm_upAbnormityRate;

    	private String fm_downAbnormityRate;

    	private String fm_abnormityLength;

		public String getFm_sampleLength() {
			return fm_sampleLength;
		}

		public void setFm_sampleLength(String fm_sampleLength) {
			this.fm_sampleLength = fm_sampleLength;
		}

		public String getFm_downThreshold() {
			return fm_downThreshold;
		}

		public void setFm_downThreshold(String fm_downThreshold) {
			this.fm_downThreshold = fm_downThreshold;
		}

		public String getFm_upThreshold() {
			return fm_upThreshold;
		}

		public void setFm_upThreshold(String fm_upThreshold) {
			this.fm_upThreshold = fm_upThreshold;
		}

		public String getFm_upAbnormityRate() {
			return fm_upAbnormityRate;
		}

		public void setFm_upAbnormityRate(String fm_upAbnormityRate) {
			this.fm_upAbnormityRate = fm_upAbnormityRate;
		}

		public String getFm_downAbnormityRate() {
			return fm_downAbnormityRate;
		}

		public void setFm_downAbnormityRate(String fm_downAbnormityRate) {
			this.fm_downAbnormityRate = fm_downAbnormityRate;
		}

		public String getFm_abnormityLength() {
			return fm_abnormityLength;
		}

		public void setFm_abnormityLength(String fm_abnormityLength) {
			this.fm_abnormityLength = fm_abnormityLength;
		}
    	
    	
    }
    
    public static class AmParam{
    	private String sampleLength; 
    	private String downThreshold;
    	private String  upThreshold;
    	private String upAbnormityRate;
    	private String downAbnormityRate; 
    	private String abnormityLength;
		public String getSampleLength() {
			return sampleLength;
		}
		public void setSampleLength(String sampleLength) {
			this.sampleLength = sampleLength;
		}
		public String getDownThreshold() {
			return downThreshold;
		}
		public void setDownThreshold(String downThreshold) {
			this.downThreshold = downThreshold;
		}
		public String getUpThreshold() {
			return upThreshold;
		}
		public void setUpThreshold(String upThreshold) {
			this.upThreshold = upThreshold;
		}
		public String getUpAbnormityRate() {
			return upAbnormityRate;
		}
		public void setUpAbnormityRate(String upAbnormityRate) {
			this.upAbnormityRate = upAbnormityRate;
		}
		public String getDownAbnormityRate() {
			return downAbnormityRate;
		}
		public void setDownAbnormityRate(String downAbnormityRate) {
			this.downAbnormityRate = downAbnormityRate;
		}
		public String getAbnormityLength() {
			return abnormityLength;
		}
		public void setAbnormityLength(String abnormityLength) {
			this.abnormityLength = abnormityLength;
		}
    	
    	
    }
}























