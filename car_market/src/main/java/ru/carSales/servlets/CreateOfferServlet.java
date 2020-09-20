package ru.carSales.servlets;

import lombok.SneakyThrows;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ru.carSales.models.Offer;
import ru.carSales.models.UserForSales;
import ru.carSales.storage.ValidateCarService;
import ru.carSales.storage.operations.Actions;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * Created by Intellij IDEA.
 * User: Vitaly Zubov.
 * Email: Zubov.VP@yandex.ru.
 * Version: $Id$.
 * Date: 02.07.2020.
 */
@MultipartConfig
public class CreateOfferServlet extends HttpServlet {
    private static Random random = new Random();
    private static Actions<Offer> VALIDATE = ValidateCarService.getInstance();

    /**
     * Show CreateOffer.jsp.
     *
     * @param req - req.
     * @param resp - resp
     * @throws ServletException - ServletException.
     * @throws IOException - IOException.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/CreateOffer.jsp").forward(req, resp);
    }

    /**
     * Add offer in DB.
     *
     * @param req - req.
     * @param resp - resp.
     * @throws IOException - IOException.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Offer offer = new Offer();
        offer.setDate(LocalDate.now());
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        if (!isMultipart) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024 * 1024);
        File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(tempDir);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(1024 * 1024 * 10);
        UserForSales user = new UserForSales();
        user.setId((Integer) req.getSession().getAttribute("id"));
        offer.setUser(user);
        try {
            List items = upload.parseRequest(req);
            Iterator iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    processFormField(offer, item);
                } else {
                    processUploadedFile(offer, item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        if (VALIDATE.add(offer) == null) {
            resp.sendError(406);
        }
        resp.sendRedirect(String.format("%s/start", req.getContextPath()));
    }

    /**
     * Upload file.
     *
     * @param car - offer.
     * @param item - item.
     * @throws Exception - Exception.
     */
    private void processUploadedFile(Offer car, FileItem item) throws Exception {
        File uploadetFile = new File("C:\\projects\\job4j_hibernate\\car_market\\src\\main\\webapp\\images");
        String name;
        if (!uploadetFile.exists() && uploadetFile.isFile()) {
            uploadetFile.mkdir();
        }
        do {
            name = random.nextInt() + item.getName();
            uploadetFile = new File(String.format("C:\\projects\\job4j_hibernate\\car_market\\src\\main\\webapp\\images\\%s", name));
        } while (uploadetFile.exists());
        uploadetFile.createNewFile();
        item.write(uploadetFile);
        car.setDir_photos(name);
    }

    /**
     * Fill parameters.
     *
     * @param car - offer.
     * @param item - item.
     */
    private void processFormField(Offer car, FileItem item) {
        if (item.getFieldName().equals("category")) {
            car.setCategory(item.getString());
        } else if (item.getFieldName().equals("mark")) {
            car.setMark(item.getString());
        } else if (item.getFieldName().equals("year_of_issue")) {
            car.setYearOfIssue(Integer.parseInt(item.getString()));
        } else if (item.getFieldName().equals("type_body")) {
            car.setTypeBody(item.getString());
        } else if (item.getFieldName().equals("transmission")) {
            car.setTransmission(item.getString());
        } else if (item.getFieldName().equals("price")) {
            car.setPrice(Integer.parseInt(item.getString()));
        }
    }

    /**
     * Close ValidateCarService and destroy CreateOfferServlet.
     */
    @SneakyThrows
    @Override
    public void destroy() {
        VALIDATE.close();
        super.destroy();
    }
}
