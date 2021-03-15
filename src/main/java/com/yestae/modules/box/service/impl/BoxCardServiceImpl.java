package com.yestae.modules.box.service.impl;


import com.yestae.box.api.BoxResult;
import com.yestae.box.api.IBoxDubboService;
import com.yestae.box.dto.BindRequest;
import com.yestae.box.dto.SimpleCardInfo;
import com.yestae.common.enums.ApiErrorMsgEnums;
import com.yestae.common.exception.RRException;
import com.yestae.modules.box.service.BoxCardService;
import com.yestae.modules.box.vo.CardExchangeRequest;
import com.yestae.modules.box.vo.CardInfo;
import com.yestae.modules.box.vo.CardQueryRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangpeng
 * @title: CouponServiceImpl
 * @packageName: com.yestae.modules.coupon.service.impl
 * @description: 茶票实现
 * @date 2020-02-18 14:40
 */
@Service("boxCardService")
@Slf4j
public class BoxCardServiceImpl implements BoxCardService {

    @Autowired
    private IBoxDubboService boxDubboService;


    /**
     *
     * 根据兑换码查找卡信息
     *
     * @param cardQueryRequest
     *            兑换码
     * @return 卡片信息
     */
    @Override
    public CardInfo findCardInfoByCardCode(CardQueryRequest cardQueryRequest){
        String cardCode = cardQueryRequest.getCardCode();
        BoxResult<SimpleCardInfo> boxResult = boxDubboService.findCardInfoByCardCode(cardCode);
        if(boxResult ==null || !boxResult.isSucceed() || boxResult.getResult() == null){
            throw new RRException(
                    ApiErrorMsgEnums.CARD_NOT_EXSIT.getDesc(),
                    ApiErrorMsgEnums.CARD_NOT_EXSIT.getErrCode());

        }
        SimpleCardInfo  simpleCardInfo = boxResult.getResult();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(simpleCardInfo.getCardNumber());
        cardInfo.setProductNo(simpleCardInfo.getGoodsNumber());
        cardInfo.setUseExpiryTime(simpleCardInfo.getUseExpiryTime());
        return cardInfo;
    }

    /**
     *
     * 兑换
     *
     * @param request
     *            宝盒服务兑换请求对象
     * @return 宝盒卡片信息
     *
     */
    @Override
    public CardInfo exchange(CardExchangeRequest request){
        BindRequest bindRequest = new BindRequest();
        BeanUtils.copyProperties(request,bindRequest);
        BoxResult<SimpleCardInfo> boxResult = boxDubboService.checkCodeAndUpdate(bindRequest);
        if(boxResult == null){
            throw new RRException(
                    ApiErrorMsgEnums.SYSTEM_ERROR.getDesc(),
                    ApiErrorMsgEnums.SYSTEM_ERROR.getErrCode());
        }
        if(!boxResult.isSucceed()){
            throw new RRException(boxResult.getRetMsg(),
                    ApiErrorMsgEnums.CARD_EXCHANGE_FAIL.getDesc());
        }
        SimpleCardInfo simpleCardInfo  = boxResult.getResult();
        CardInfo cardInfo = new CardInfo();
        cardInfo.setCardNumber(simpleCardInfo.getCardNumber());
        cardInfo.setProductNo(simpleCardInfo.getGoodsNumber());
        cardInfo.setUseExpiryTime(simpleCardInfo.getUseExpiryTime());
        return cardInfo;
    }
}
