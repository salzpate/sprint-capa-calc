package de.salzpaten.tools.scc.service;

import java.io.IOException;
import java.util.List;

import de.salzpaten.tools.scc.domain.CalcTableData;

/**
 * Service for getting table data from an external System
 *
 * @author Ronny Krammer
 *
 */
public interface DataService {

	/**
	 * Getting {@link CalcTableData} by Id
	 *
	 * @param id Id
	 * @return {@link CalcTableData}
	 * @throws IOException          IOException
	 * @throws InterruptedException InterruptedException
	 */
	CalcTableData getCalcTableData(String id) throws IOException, InterruptedException;

	/**
	 * Getting a List of {@link CalcTableData} by the given value
	 *
	 * @param value Value to get the list
	 * @return List of {@link CalcTableData}
	 * @throws IOException          IOException
	 * @throws InterruptedException InterruptedException
	 */
	List<CalcTableData> getCalcTableDataList(String value) throws IOException, InterruptedException;

	/**
	 * Is the data serive enabled
	 *
	 * @return data serive enabled
	 */
	boolean isEnabled();

}
