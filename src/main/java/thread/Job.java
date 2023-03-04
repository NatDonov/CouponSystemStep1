package thread;


import dao.CouponsDAO;
import dao.CouponsDAOImpl;

import java.sql.SQLException;


public class Job implements Runnable {
    private CouponsDAO couponsDAO;

    private final int TIME_TO_SLEEP = 1000 * 60 * 60 * 24;
    private boolean quit;

    public Job() {
        this.couponsDAO = new CouponsDAOImpl();
        this.quit = false;
    }

    @Override
    public void run() {
        while (!quit) {
            try {
                couponsDAO.deleteALLExpiredCoupon();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(TIME_TO_SLEEP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public void stopRun() {
        this.quit = true;
    }

}
