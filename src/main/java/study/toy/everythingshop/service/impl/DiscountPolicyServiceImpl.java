package study.toy.everythingshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import study.toy.everythingshop.logTrace.Trace;
import study.toy.everythingshop.repository.DiscountPolicyDAO;
import study.toy.everythingshop.service.DiscountPolicyService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Trace
public class DiscountPolicyServiceImpl implements DiscountPolicyService {

    private final DiscountPolicyDAO discountPolicyDAO;

}
