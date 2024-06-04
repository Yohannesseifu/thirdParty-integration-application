<template>
  <div>
    <h2 id="page-heading" data-cy="ThirdPartyIntegrationHeading">
      <span id="third-party-integration-heading">Third Party Integrations</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon> <span>Refresh list</span>
        </button>
        <router-link :to="{ name: 'ThirdPartyIntegrationCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-third-party-integration"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span>Create a new Third Party Integration</span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && thirdPartyIntegrations && thirdPartyIntegrations.length === 0">
      <span>No Third Party Integrations found</span>
    </div>
    <div class="table-responsive" v-if="thirdPartyIntegrations && thirdPartyIntegrations.length > 0">
      <table class="table table-striped" aria-describedby="thirdPartyIntegrations">
        <thead>
          <tr>
            <th scope="row"><span>ID</span></th>
            <th scope="row"><span>Is Draft</span></th>
            <th scope="row"><span>Integration Name</span></th>
            <th scope="row"><span>Company Name</span></th>
            <th scope="row"><span>Description</span></th>
            <th scope="row"><span>Icon Path</span></th>
            <th scope="row"><span>Enabled</span></th>
            <th scope="row"><span>Account Number</span></th>
            <th scope="row"><span>Minimum Amount</span></th>
            <th scope="row"><span>Maximum Amount</span></th>
            <th scope="row"><span>Currency Code</span></th>
            <th scope="row"><span>Payment Confirmation Template</span></th>
            <th scope="row"><span>Payment Detail Template</span></th>
            <th scope="row"><span>Integration Category</span></th>
            <th scope="row"><span>Visiblity</span></th>
            <th scope="row"><span>Confirm Recipient Identity</span></th>
            <th scope="row"><span>Category Menus</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="thirdPartyIntegration in thirdPartyIntegrations" :key="thirdPartyIntegration.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ThirdPartyIntegrationView', params: { thirdPartyIntegrationId: thirdPartyIntegration.id } }">{{
                thirdPartyIntegration.id
              }}</router-link>
            </td>
            <td>{{ thirdPartyIntegration.isDraft }}</td>
            <td>{{ thirdPartyIntegration.integrationName }}</td>
            <td>{{ thirdPartyIntegration.companyName }}</td>
            <td>{{ thirdPartyIntegration.description }}</td>
            <td>{{ thirdPartyIntegration.iconPath }}</td>
            <td>{{ thirdPartyIntegration.enabled }}</td>
            <td>{{ thirdPartyIntegration.accountNumber }}</td>
            <td>{{ thirdPartyIntegration.minimumAmount }}</td>
            <td>{{ thirdPartyIntegration.maximumAmount }}</td>
            <td>{{ thirdPartyIntegration.currencyCode }}</td>
            <td>{{ thirdPartyIntegration.paymentConfirmationTemplate }}</td>
            <td>{{ thirdPartyIntegration.paymentDetailTemplate }}</td>
            <td>{{ thirdPartyIntegration.integrationCategory }}</td>
            <td>{{ thirdPartyIntegration.visiblity }}</td>
            <td>{{ thirdPartyIntegration.confirmRecipientIdentity }}</td>
            <td>
              <span v-for="(categoryMenus, i) in thirdPartyIntegration.categoryMenus" :key="categoryMenus.id"
                >{{ i > 0 ? ', ' : '' }}
                <router-link class="form-control-static" :to="{ name: 'MenuView', params: { menuId: categoryMenus.id } }">{{
                  categoryMenus.id
                }}</router-link>
              </span>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'ThirdPartyIntegrationView', params: { thirdPartyIntegrationId: thirdPartyIntegration.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'ThirdPartyIntegrationEdit', params: { thirdPartyIntegrationId: thirdPartyIntegration.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(thirdPartyIntegration)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <template #modal-title>
        <span
          id="thirdPartyIntegrationApplicationApp.thirdPartyIntegration.delete.question"
          data-cy="thirdPartyIntegrationDeleteDialogHeading"
          >Confirm delete operation</span
        >
      </template>
      <div class="modal-body">
        <p id="jhi-delete-thirdPartyIntegration-heading">Are you sure you want to delete Third Party Integration {{ removeId }}?</p>
      </div>
      <template #modal-footer>
        <div>
          <button type="button" class="btn btn-secondary" v-on:click="closeDialog()">Cancel</button>
          <button
            type="button"
            class="btn btn-primary"
            id="jhi-confirm-delete-thirdPartyIntegration"
            data-cy="entityConfirmDeleteButton"
            v-on:click="removeThirdPartyIntegration()"
          >
            Delete
          </button>
        </div>
      </template>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./third-party-integration.component.ts"></script>
