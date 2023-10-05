package fr.hb.asl.calendrier_gif.view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import fr.hb.asl.calendrier_gif.business.Gif;
import fr.hb.asl.calendrier_gif.business.Jour;
import fr.hb.asl.calendrier_gif.service.GifService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalendrierExportPDF extends AbstractPdfView {

	private GifService gifService;

	@Override
	@SuppressWarnings("unchecked")
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		document.setPageSize(PageSize.A4.rotate());
		document.newPage();

		List<Jour> jours = (List<Jour>) model.get("jours");

		PdfPTable table = new PdfPTable(4);
		table.addCell("Jour");
		table.addCell("Gif");
		table.addCell("DateHeureGif");
		table.addCell("Points");

		for (Jour jour : jours) {
			Gif gif = gifService.recupererGifParJour(jour);
			table.addCell(jour.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
			if (gif != null) {
				if (gifService.recupererGifDistant(gif.getId()) != null) {
					table.addCell(gifService.recupererGifDistant(gif.getId()).getUrl());
				} else {
					table.addCell(gifService.recupererGifTeleverse(gif.getId()).getNomFichierOriginal());
				}
				table.addCell(
						jour.getGif().getDateHeureAjout().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm")));
			} else {
				table.addCell("Aucun gif.");
				table.addCell("Libre!");
			}
			table.addCell(jour.getNbPoints() + " points");
		}
		document.add(table);
		document.close();
	}

}
