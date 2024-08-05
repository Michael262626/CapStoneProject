package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.*;
import com.africa.semiclon.capStoneProject.dtos.response.*;

public interface AdminService {
    ManageUserResponse manageUsers(ManageUsersRequest request);
    ViewWasteResponse viewAllWaste(ViewWasteRequest viewWasteRequest);
    AssignWasteResponse assignWasteToAgent(AssignWasteRequest request);
    WasteReportResponse generateWasteReport(GenerateWasteReportRequest request);
    NotificationResponse sendNotificationRequest(NotificationRequest notificationRequest);
    DeleteUserResponse deleteUser(DeleteUserRequest deleteRequest);
    RegisterAgentResponse registerAgent(RegisterAgentRequest registerRequest);
    RegisterWasteResponse registerWasteForSale(RegisterWasteRequest registerWasteRequest);

}
