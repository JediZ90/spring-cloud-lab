package io.seata.sample.action;

import org.springframework.stereotype.Service;

import io.seata.rm.tcc.api.BusinessActionContext;

@Service
public class TccActionOneImpl implements TccActionOne {

	@Override
	public boolean prepare(BusinessActionContext actionContext, int a) {
		String xid = actionContext.getXid();
		System.out.println("TccActionOne prepare, xid:" + xid);
		return true;
	}

	@Override
	public boolean commit(BusinessActionContext actionContext) {
		String xid = actionContext.getXid();
		System.out.println("TccActionOne commit, xid:" + xid);
		return true;
	}

	@Override
	public boolean rollback(BusinessActionContext actionContext) {
		String xid = actionContext.getXid();
		System.out.println("TccActionOne rollback, xid:" + xid);
		return true;
	}
}
