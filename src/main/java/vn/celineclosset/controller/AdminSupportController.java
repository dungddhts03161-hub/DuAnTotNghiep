package vn.celineclosset.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.celineclosset.dao.SupportDAO;

import java.io.IOException;

@WebServlet("/admin/support")
public class AdminSupportController extends BaseController {
    private final SupportDAO supportDAO = new SupportDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!requireBackOffice(req, resp)) return;
        if ("DELIVERY".equals(currentRole(req))) { resp.sendRedirect(adminStartUrl(req)); return; }
        try {
            String role = currentRole(req);
            int accountId = authId(req);
            if ("ADMIN".equals(role)) req.setAttribute("staffList", accountDAO.employeesByRole("STAFF"));

            String id = req.getParameter("id");
            if (id != null && !id.isBlank()) {
                int requestId = Integer.parseInt(id);
                supportDAO.markCustomerMessagesRead(requestId, role, accountId);
                req.setAttribute("selectedRequest", supportDAO.requestForBackOffice(requestId, role, accountId));
                req.setAttribute("messages", supportDAO.messages(requestId));
            }
            req.setAttribute("requests", supportDAO.backOfficeRequests(role, accountId, req.getParameter("status")));
            view(req, resp, "admin/support.jsp");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!requireBackOffice(req, resp)) return;
        if ("DELIVERY".equals(currentRole(req))) { resp.sendRedirect(adminStartUrl(req)); return; }
        try {
            int requestId = Integer.parseInt(req.getParameter("maYC"));
            String role = currentRole(req);
            String action = req.getParameter("action");

            if ("assign".equals(action)) {
                if (!"ADMIN".equals(role)) {
                    resp.sendRedirect(req.getContextPath() + "/admin/support?error=permission");
                    return;
                }
                supportDAO.assign(requestId, Integer.parseInt(req.getParameter("staffId")));
            } else {
                supportDAO.reply(requestId, authId(req), role,
                        req.getParameter("phanHoi"), req.getParameter("trangThai"));
            }
            resp.sendRedirect(req.getContextPath() + "/admin/support?id=" + requestId + "&saved=1");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
