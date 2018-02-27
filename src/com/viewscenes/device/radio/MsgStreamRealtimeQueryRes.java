package com.viewscenes.device.radio;

import com.viewscenes.device.*;
import java.util.*;

import com.viewscenes.device.framework.BaseMessageResponse;
import com.viewscenes.device.framework.IMessageParser;
import com.viewscenes.device.framework.MessageElement;
import com.viewscenes.device.util.InnerMsgType;

/**
 *
 * <p>Title : 视音频实时查询解析类 </p>
 *
 * <p>Description: 兼容v5、v6、v7、v8版本和边境广播、电视V1版本。</p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Viewscenes</p>
 *
 * @author 赵广磊
 * @version 1.0
 */

public class MsgStreamRealtimeQueryRes extends BaseMessageResponse {

    private Map urls;

    public MsgStreamRealtimeQueryRes() {

        setParsers(
            new IMessageParser[] {

//                   new ParserV1FrontierRadio(),  new ParserV1FrontierVideo(),
//                   new ParserV4Radio(), new ParserV5Radio(),
//                   new ParserV6Radio(), new ParserV7Radio(),
                   new ParserV8Radio()//, 
//                   new ParserV8Video()
            }
        );
    }

    public Map getUrls() {

        if (urls == null)
            throw new AssertionError();
        else
            return urls;
    }

    public String getUrl(String index) {

        if (urls == null)
            throw new AssertionError();
        else
            return (String)urls.get(index);
    }

    public int size() {

        if (urls == null)
            return 0;
        else
            return urls.size();
    }

//    /**
//     *
//     * 边境广播V1版具体实现
//     *
//     */
//    protected class ParserV1FrontierRadio extends BaseMessageResponse.ParserV1FrontierRadio {
//
//        protected ParserV1FrontierRadio() {
//
//        }
//
//        protected void doParse() {
//
//            MessageElement e = getBody();
//
//            if (e != null) {
//                Collection mediaStreams = getBody().getChildren();
//
//                if (mediaStreams != null) {
//
//                    urls = new LinkedHashMap(mediaStreams.size());
//                    MessageElement channelScan;
//                    int index = 0;
//
//                    for (Iterator it = mediaStreams.iterator(); it.hasNext();
//                                       urls.put(String.valueOf(index), channelScan.getAttributes().get("url"))) {
//                        channelScan = (MessageElement)it.next();
//                    }
//
//                    index++;
//                }
//
//            }
//
//        }
//
//    }
//
//    /**
//     *
//     * 边境电视V1版具体实现
//     *
//     */
//    protected class ParserV1FrontierVideo extends BaseMessageResponse.ParserV1FrontierVideo {
//
//        protected ParserV1FrontierVideo() {
//
//        }
//
//        protected void doParse() {
//
//            MessageElement e = getBody();
//
//            if (e != null) {
//
//                Collection mediaStreams = getBody().getChildren();
//
//                if (mediaStreams != null) {
//
//                    urls = new LinkedHashMap(mediaStreams.size());
//                    MessageElement channelScan;
//                    int index = 0;
//
//                    for (Iterator it = mediaStreams.iterator(); it.hasNext();
//                                       urls.put(String.valueOf(index), channelScan.getAttributes().get("url"))) {
//                        channelScan = (MessageElement)it.next();
//                    }
//
//                    index++;
//                }
//
//            }
//
//        }
//
//    }

//    /**
//     *
//     * V8版具体实现
//     *
//     */
//    protected class ParserV8Video extends MsgStreamRealtimeQueryRes.ParserV4Radio {
//
//        protected ParserV8Video() {
//
//        }
//
//        public InnerMsgType getType() {
//
//            return InnerMsgType.instance("video8");
//        }
//    }

    /**
     *
     * V8版具体实现
     *
     */
    protected class ParserV8Radio extends MsgStreamRealtimeQueryRes.ParserV8BaseRadio {

        protected ParserV8Radio() {

        }

        public InnerMsgType getType() {

            return InnerMsgType.instance("radio8");
        }
    }

//    /**
//     *
//     * V7版具体实现
//     *
//     */
//    protected class ParserV7Radio extends MsgStreamRealtimeQueryRes.ParserV4Radio {
//
//        protected ParserV7Radio() {
//
//        }
//
//        public InnerMsgType getType() {
//
//            return InnerMsgType.instance("radio7");
//        }
//    }
//
//    /**
//     *
//     * V6版具体实现
//     *
//     */
//    protected class ParserV6Radio extends MsgStreamRealtimeQueryRes.ParserV4Radio {
//
//        protected ParserV6Radio() {
//
//        }
//
//        public InnerMsgType getType() {
//
//            return InnerMsgType.instance("radio6");
//        }
//    }
//
//    /**
//     *
//     * V5版具体实现
//     *
//     */
//    protected class ParserV5Radio extends MsgStreamRealtimeQueryRes.ParserV4Radio {
//
//        protected ParserV5Radio() {
//
//        }
//
//        public InnerMsgType getType() {
//
//            return InnerMsgType.instance("radio5");
//        }
//    }

    /**
     *
     * V4版具体实现
     *
     */
    protected class ParserV8BaseRadio extends BaseMessageResponse.ParserV8BaseRadio {

        protected ParserV8BaseRadio() {

        }

        protected void doParse() {

            MessageElement e = getBody();
            if (e != null) {
                Collection mediaStreams = getBody().getChildren();
                if (mediaStreams != null) {
                    urls = new LinkedHashMap(mediaStreams.size());
                    MessageElement channelScan;

                    for (Iterator it = mediaStreams.iterator(); it.hasNext();
                                       urls.put(channelScan.getAttributes().get("index"), channelScan.getAttributes().get("url"))) {
                        channelScan = (MessageElement)it.next();
                    }
                }

            }

        }

    }

}















