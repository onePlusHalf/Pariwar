package com.mkasana.FamilyTree.Pariwar.Component.CommonAPIs;

import com.mkasana.FamilyTree.Pariwar.model.SearchFilters;
import com.mkasana.FamilyTree.Pariwar.model.SearchFiltersResponse;
import com.mkasana.FamilyTree.Pariwar.model.UserFullDetails;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CommonAPIsComponent {

    public UserFullDetails getUserFullDetailsByToken(final String token);

    public List<UserFullDetails> getUserParentsDetailsByUserId(final int userId);

    public List<UserFullDetails> getBasicUserParentsDetailsByUserId(final int userId);

    public List<UserFullDetails> getUserSiblingsDetailsByUserId(final int userId);

    public List<UserFullDetails> getBasicUserSiblingsDetailsByUserId(final int userId);

    public List<UserFullDetails> getUserChildsDetailsByUserId(final int userId);

    public List<UserFullDetails> getBasicUserChildsDetailsByUserId(final int userId);

    public List<UserFullDetails> getBasicUserspouseDetailsByUserId(final int userId);

    public List<SearchFiltersResponse> searchUsersBasedOnPassedConstrains(final SearchFilters filters);

    public void addParentToLoggedInUser(final int userId, final int parentId);

    public void addChildToLoggedInUser(final int userId, final int childId);

    public void addSpouseToUser(final int userId, final int spouseId);
}
