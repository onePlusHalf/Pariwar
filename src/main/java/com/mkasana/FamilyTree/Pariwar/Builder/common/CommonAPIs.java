package com.mkasana.FamilyTree.Pariwar.Builder.common;


import com.mkasana.FamilyTree.Pariwar.dao.commonAPIs.CommonAPIsDAO;
import com.mkasana.FamilyTree.Pariwar.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.*;
import java.util.stream.Collectors;

@Configurable
@Component
public class CommonAPIs {

    @Autowired
    private CommonAPIsDAO userCommonAPIsDAO;

    public userRegistrationRequest getBasicUserDetailsByUsername(final String username) {
        try {
            ResultSet resultSet = userCommonAPIsDAO.getBasicUserDetailsByUsername(username);
            if(resultSet.next()) {
               return ConvertResultSetToObject(resultSet);
            }
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

    private userRegistrationRequest ConvertResultSetToObject(ResultSet resultSet) {
        userRegistrationRequest details = new userRegistrationRequest();
        try {
            details.setUsername(resultSet.getString("Username"));
            details.setFirstname(resultSet.getString("FirstName"));
            details.setPassword(resultSet.getString("Passphrase"));
            details.setDateofbirth(resultSet.getString("DateOfBirth"));
            details.setUserId(resultSet.getInt("Id"));
            return details;
        } catch(Exception e) {
            return null;
        }
    }


    public boolean setSessionDetails(final int UserId,final String username, final String GUID) {
        try {
            userCommonAPIsDAO.setSessionDetails(UserId, username, GUID);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to add the session details for the user");
            return false;
        }
    }

    public boolean updateSessionDetailsLastUse(final String GUID) {
        try {
            userCommonAPIsDAO.updateSessionDetailsLastUse(GUID);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to update Session Details LastUse the session details for the user");
            return false;
        }
    }

    public boolean deleteOldSessionDetails() {
        try {
            userCommonAPIsDAO.deleteOldSessionDetails();
            return true;
        } catch (Exception e) {
            System.out.println("Failed to Delete old Session Details");
            return false;
        }
    }

    public SessionDetails getSessionDetailsByToken(String GUID) {
        try {
            ResultSet resultSet = userCommonAPIsDAO.getSessionDetailsByToken(GUID);
            if(resultSet.next()) {
                return ConvertResultSetToSessionObject(resultSet);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Failed to get Session Details");
            return null;
        }
    }

    private SessionDetails ConvertResultSetToSessionObject(ResultSet resultSet) {
        SessionDetails sessionDetails = new SessionDetails();
        try {
            sessionDetails.setUsername(resultSet.getString("Username"));
            sessionDetails.setUserId(resultSet.getInt("UserId"));
            sessionDetails.setToken(resultSet.getString("Token"));
            return sessionDetails;
        } catch(Exception e) {
            return null;
        }
    }

    public UserFullDetails getUserInfoDetailsByUserId(final int userId) {
        try {
            ResultSet resultSet = userCommonAPIsDAO.getUserInfoDetailsByUserId(userId);
            if(resultSet.next()) {
                return ConvertResultSetToUserFullDetailsObject(resultSet);
            }
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

    private UserFullDetails ConvertResultSetToUserFullDetailsObject(ResultSet resultSet) {
        UserFullDetails details = new UserFullDetails();
        try {
            details.setUsername(resultSet.getString("Username"));
            details.setFirstname(resultSet.getString("FirstName"));
            //details.setPassword(resultSet.getString("Passphrase"));
            details.setDateofbirth(resultSet.getString("DateOfBirth"));
            details.setUserId(resultSet.getInt("Id"));
            details.setGender(resultSet.getInt("Gender"));

            userRegistrationContactDetails contactDetails = new userRegistrationContactDetails();
            contactDetails.setEmailAddress(resultSet.getString("Email"));
            contactDetails.setPhone(resultSet.getString("Phone"));
            details.setContactDetails(contactDetails);

            UserReligiousDetails userReligiousDetails = new UserReligiousDetails();
            userReligiousDetails.setIdReligious(resultSet.getInt("SubcasteReligion"));
            details.setUserReligiousDetails(userReligiousDetails);

            UserAddressDetails userAddressDetails = new UserAddressDetails();
            userAddressDetails.setIdAddress(resultSet.getInt("UserAddressDetailsId"));
            details.setUserAddressDetails(userAddressDetails);

            return details;
        } catch(Exception e) {
            return null;
        }
    }

    public userRegistrationAddressDetails getRegistrationAddressDetailsByAddressDetailsId(final int addressDetailId) {
        try {
            ResultSet resultSet = userCommonAPIsDAO.getRegistrationAddressDetailsByAddressDetailsId(addressDetailId);
            if(resultSet.next()) {
                return ConvertResultSetTouserRegistrationAddressDetailsObject(resultSet);
            }
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

    private userRegistrationAddressDetails ConvertResultSetTouserRegistrationAddressDetailsObject(ResultSet resultSet) {
        userRegistrationAddressDetails details = new userRegistrationAddressDetails();
        try {
            details.setLocality(resultSet.getString("firstAddress"));
            details.setState(resultSet.getInt("StateId"));
            details.setDistrict(resultSet.getInt("DistrictId"));
            details.setTehsil(resultSet.getInt("TehsilId"));
            details.setVillage(resultSet.getInt("VillageId"));
            return details;
        } catch(Exception e) {
            return null;
        }
    }

    public userRegistrationReligionDetails getRegistrationReligionDetailsByReligiousDetailId(final int ReligiousDetailsId) {
        try {
            ResultSet resultSet = userCommonAPIsDAO.getRegistrationReligionDetailsByReligiousDetailId(ReligiousDetailsId);
            if(resultSet.next()) {
                return ConvertResultSetTouserRegistrationReligionDetailsObject(resultSet);
            }
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
        return null;
    }

    public List<Integer> getUserParentIdList(final int userId) {
        try {
            ResultSet resultSet = userCommonAPIsDAO.getUserParentIdList(userId);
            List<Integer> parentList = new ArrayList<>();
            while(resultSet.next()) {
                parentList.add(resultSet.getInt("ParentId"));
            }
            return parentList;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public List<Integer> getUserChildsIdList(final int userId) {
        try {
            ResultSet resultSet = userCommonAPIsDAO.getUserChildsIdList(userId);
            List<Integer> childsList = new ArrayList<>();
            while(resultSet.next()) {
                childsList.add(resultSet.getInt("ChildId"));
            }
            return childsList;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    public List<Integer> getUserSpouseIdList(final int userId) {
        try {
            ResultSet resultSet = userCommonAPIsDAO.getUserSpouseIdList(userId);
            List<Integer> spouseList = new ArrayList<>();
            while(resultSet.next()) {
                spouseList.add(resultSet.getInt("SpouseId"));
            }
            return spouseList;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    private userRegistrationReligionDetails ConvertResultSetTouserRegistrationReligionDetailsObject(ResultSet resultSet) {
        userRegistrationReligionDetails details = new userRegistrationReligionDetails();
        try {
            details.setReligion(resultSet.getInt("ReligionId"));
            details.setCaste(resultSet.getInt("CasteId"));
            details.setSubCaste(resultSet.getInt("SubCasteId"));
            return details;
        } catch(Exception e) {
            return null;
        }
    }


    public List<SearchFiltersResponse> searchUsersBasedOnPassedConstrains(final SearchFilters filters) {
        try {
            ResultSet resultSet = userCommonAPIsDAO.searchUsersBasedOnPassedConstrains(filters);
            List<SearchFiltersResponse> userList = new ArrayList<>();
            while(resultSet.next()) {
                SearchFiltersResponse user = new SearchFiltersResponse();
                user.setUserid(resultSet.getInt("Id"));
                user.setName(resultSet.getString("name"));
                user.setUsername(resultSet.getString("username"));
                user.setGender(resultSet.getInt("gender"));
                user.setLocaladdress(resultSet.getString("localaddress"));
                user.setAddress(resultSet.getString("address"));
                user.setReligion(resultSet.getString("religion"));
                userList.add(user);
                //spouseList.add(resultSet.getInt("SpouseId"));
            }
            return userList;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    /**
     * this is to filter out the names
     * @return
     */
    public List<SearchFiltersResponse> filterByNameOnTheSearchList(final List<SearchFiltersResponse> listOfUsers, final String name) {
        String[] nameList = name.toLowerCase().trim().split("\\s+");
        if(name == null || name.length() <= 0 || Arrays.asList(nameList).size() <= 0)
            return listOfUsers;
        return listOfUsers.stream().filter(user -> isNameMatched(nameList, user.getName())).collect(Collectors.toList());
    }

    private boolean isNameMatched(final String[] nameList, final String userName) {
        String[] userNameList = userName.toLowerCase().trim().split("\\s+");
        for(String word : nameList) {
            if(Arrays.asList(userNameList).contains(word))
                return true;
        }
        return false;
    }


    public void addParentToLoggedInUser(final int userId, final int parentId) {
        try {
            userCommonAPIsDAO.addParentToUserId(userId, parentId);
            userCommonAPIsDAO.addChildToParentId(parentId, userId);
        } catch (Exception e) {
            System.out.println("Failed to Add Parent to User");
        }
    }

    public void addChildToLoggedInUser(final int userId, final int childId) {
        try {
            userCommonAPIsDAO.addParentToUserId(childId, userId);
            userCommonAPIsDAO.addChildToParentId(userId, childId);
        } catch (Exception e) {
            System.out.println("Failed to Add Parent to User");
        }
    }

    public void addSpouseToUser(final int userId, final int spouseId) {
        try {
            userCommonAPIsDAO.addSpouseToUser(spouseId, userId);
            userCommonAPIsDAO.addSpouseToUser(userId, spouseId);
        } catch (Exception e) {
            System.out.println("Failed to Add Spouse to User");
        }
    }

    public void addSiblingToUser(final int userId, final int creatorUserId) {
        try {
            List<Integer> parentList = getUserParentIdList(creatorUserId);
            Set<Integer> tmp = new HashSet<>();
            List<Integer> uniqueParentList = parentList.stream().filter(i -> tmp.add(i)).collect(Collectors.toList());
            for(Integer i : uniqueParentList) {
                userCommonAPIsDAO.addParentToUserId(userId, i);
                userCommonAPIsDAO.addChildToParentId(i, userId);
            }
        } catch (Exception e) {
            System.out.println("Failed to Add Sibling to the user");
        }
    }
}