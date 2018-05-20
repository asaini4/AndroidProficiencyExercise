package com.wipro.androidproficiencyexercise.model;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wipro.androidproficiencyexercise.R;
import com.wipro.androidproficiencyexercise.application.AppController;
import com.wipro.androidproficiencyexercise.interfaces.AppInterfaces;
import com.wipro.androidproficiencyexercise.pojo.AppPojo;
import com.wipro.androidproficiencyexercise.pojo.Row;
import com.wipro.androidproficiencyexercise.pojo.WSResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class NetworkModel implements AppInterfaces.Action {

    private AppInterfaces.ModelInterface appPresenter;
    private AppPojo appPojoObj;

    public NetworkModel(AppInterfaces.ModelInterface appPresenter, AppPojo appPojoObj) {
        this.appPresenter = appPresenter;
        this.appPojoObj = appPojoObj;
    }

    @Override
    public void doAction() {



            /*HurlStack hurlStack = null;

            if (URLUtil.isHttpsUrl(appPojoObj.getUrl())) { //check URL is HTTP or HTTPS
                hurlStack = new HurlStack() {
                    @Override
                    protected HttpURLConnection createConnection(URL url) throws IOException {
                        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                        try {
                            httpsURLConnection.setSSLSocketFactory(getSSLSocketFactory());
                            httpsURLConnection.setHostnameVerifier(getHostnameVerifier("certs.cac.washington.edu"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return httpsURLConnection;
                    }
                };
            }*/

        // Request a JSON response from the provided URL.
        String network_req_tag = "network_req_tag";

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, appPojoObj.getUrl(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        appPresenter.updateNetworkfModelResponseSuccess(prepareWSResponse(response));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                appPresenter.updateNetworkfModelResponseError(error.toString());
            }
        });

        AppController.mInstance.addToRequestQueue(req, network_req_tag, null);
    }

    /**
     * Preparing POJO based on JSON response.
     *
     * @param response JSON Resposne
     * @return WSResponse Passed WSResponse to presenter
     */
    private WSResponse prepareWSResponse(JSONObject response) {
        WSResponse wsResponse = new WSResponse();
        try {
            ArrayList<Row> alRow = new ArrayList<>();
            JSONArray rows = response.getJSONArray("rows");

            // looping through All Rows JSONArray
            for (int i = 0; i < rows.length(); i++) {
                Row row = new Row();
                JSONObject rowsData = rows.getJSONObject(i);

                row.setTitle(rowsData.getString("title"));
                row.setDescription(rowsData.getString("description"));
                row.setImageHref(rowsData.getString("imageHref"));

                alRow.add(row);
            }
            wsResponse.setRows(alRow);
            wsResponse.setTitle(response.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wsResponse;
    }

    // Let's assume your server app is hosting inside a server machine
    // which has a server certificate in which "Issued to" is "localhost",for example.
    // Then, inside verify method you can verify "localhost".
    // If not, you can temporarily return true
    private HostnameVerifier getHostnameVerifier(final String strHostName) {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //return true;
                //verify always returns true, which could cause insecure network traffic due to trusting TLS/SSL server certificates for wrong hostnames
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify(strHostName, session);
            }
        };
    }

    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
        return new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return originalTrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0) {
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkClientTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkClientTrusted", e.toString());
                        }
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        try {
                            if (certs != null && certs.length > 0) {
                                certs[0].checkValidity();
                            } else {
                                originalTrustManager.checkServerTrusted(certs, authType);
                            }
                        } catch (CertificateException e) {
                            Log.w("checkServerTrusted", e.toString());
                        }
                    }
                }
        };
    }

    private SSLSocketFactory getSSLSocketFactory()
            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {

        CertificateFactory cf = CertificateFactory.getInstance("X.509");

        int rawId = AppController.mInstance.getResources().getIdentifier("test", "raw", AppController.mInstance.getPackageName());

        InputStream caInput = AppController.mInstance.getResources().openRawResource(rawId); 

        Certificate ca = cf.generateCertificate(caInput);
        caInput.close();

        KeyStore keyStore = KeyStore.getInstance("BKS");
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, wrappedTrustManagers, null);

        return sslContext.getSocketFactory();
    }
}
