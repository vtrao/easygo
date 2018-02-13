package com.easygo.daolayer;

import java.util.List;

import com.easygo.model.Branch;

public interface IBranchDAO {
	public Branch getBranch(Long id) throws Exception;

	public List<Branch> getBranch() throws Exception;

	public Branch addBranch(Branch branch) throws Exception;

	public int removeBranch(Long id) throws Exception;

	public int updateBranch(Branch branch) throws Exception;
}
