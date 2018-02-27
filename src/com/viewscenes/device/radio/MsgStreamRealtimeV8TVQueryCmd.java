package com.viewscenes.device.radio;


import com.viewscenes.device.*;
import com.viewscenes.device.framework.BaseMessageCommand;
import com.viewscenes.device.framework.IMessageBuilder;
import com.viewscenes.device.framework.MessageElement;

import java.util.*;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Viewscenes</p>
 *
 * @author 赵广磊
 * @version 1.0
 */
public class MsgStreamRealtimeV8TVQueryCmd extends BaseMessageCommand {

    private Collection channels;

    public MsgStreamRealtimeV8TVQueryCmd() {

        setBuilders(
            new IMessageBuilder[] {

                    new BuilderV8Video()
            }
        );
    }

    /**
     * 设置查询的设备列表
     * @param equs RealtimeStream对象的Collection
     * @preconditions equs != null
     * @see RealtimeStream
     */

    public void setEqus(Collection equs) {

        this.channels = equs;

    }

    /**
     *
     * RealtimeStream内部类
     *
     * <p>Copyright: Copyright (c) 2009</p>
     *
     * <p>Company: Viewscenes</p>
     *
     * @author zgl
     * @version 1.0
     */
    public static class RealtimeStream {

        private String equcode, lastUrl, bps, encode, action,
                angle, zoom, channel;

        /**
         * V8视频实时
         * @param equcode String
         * @param lastUrl String
         * @param angle String
         * @param zoom String
         * @param channel String
         * @param bps String
         * @param encode String
         * @param action String
         */
        public RealtimeStream(String equcode, String lastUrl, String angle,
                              String zoom, String channel, String bps, String encode, String action) {

            this.equcode =  equcode;

            this.lastUrl = lastUrl;

            this.angle = angle;

            this.zoom = zoom;

            this.channel = channel;

            this.bps = bps;

            this.encode = encode;

            this.action = action;

        }
    }

    /**
     *
     * V8视频实时查询，具体实现
     *
     */
    protected class BuilderV8Video extends BaseMessageCommand.BuilderV8Radio {

        protected BuilderV8Video() {
        }

        protected MessageElement buildBody() {

            List list = new ArrayList(channels.size());

            int index = 0;

            for (Iterator it = channels.iterator(); it.hasNext(); ) {

                RealtimeStream cs = (RealtimeStream) it.next();

                Map map = new LinkedHashMap(9);

                map.put("Index",String.valueOf(index++));

                map.put("EquCode", cs.equcode);

                map.put("LastURL", cs.lastUrl);

                map.put("Angle", cs.angle);

                map.put("Zoom", cs.zoom);

                map.put("Channel", cs.channel);

                map.put("Bps", cs.bps);

                map.put("Encode", cs.encode);

                map.put("Action", cs.action);

                list.add(new MessageElement("RealtimeStream", map, null));

            }

            return new MessageElement("VideoRealtimeQuery", null, list);

        }
    }
}






















