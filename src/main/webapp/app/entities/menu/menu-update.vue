<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate v-on:submit.prevent="save()">
        <h2 id="thirdPartyIntegrationApplicationApp.menu.home.createOrEditLabel" data-cy="MenuCreateUpdateHeading">
          Create or edit a Menu
        </h2>
        <div>
          <div class="form-group" v-if="menu.id">
            <label for="id">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="menu.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="menu-menuName">Menu Name</label>
            <input
              type="text"
              class="form-control"
              name="menuName"
              id="menu-menuName"
              data-cy="menuName"
              :class="{ valid: !v$.menuName.$invalid, invalid: v$.menuName.$invalid }"
              v-model="v$.menuName.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="menu-menuDescription">Menu Description</label>
            <input
              type="text"
              class="form-control"
              name="menuDescription"
              id="menu-menuDescription"
              data-cy="menuDescription"
              :class="{ valid: !v$.menuDescription.$invalid, invalid: v$.menuDescription.$invalid }"
              v-model="v$.menuDescription.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="menu-iconPath">Icon Path</label>
            <input
              type="text"
              class="form-control"
              name="iconPath"
              id="menu-iconPath"
              data-cy="iconPath"
              :class="{ valid: !v$.iconPath.$invalid, invalid: v$.iconPath.$invalid }"
              v-model="v$.iconPath.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" for="menu-enabled">Enabled</label>
            <input
              type="checkbox"
              class="form-check"
              name="enabled"
              id="menu-enabled"
              data-cy="enabled"
              :class="{ valid: !v$.enabled.$invalid, invalid: v$.enabled.$invalid }"
              v-model="v$.enabled.$model"
            />
          </div>
          <div class="form-group">
            <label for="menu-dynamicPaymentMenus">Dynamic Payment Menus</label>
            <select
              class="form-control"
              id="menu-dynamicPaymentMenus"
              data-cy="dynamicPaymentMenus"
              multiple
              name="dynamicPaymentMenus"
              v-if="menu.dynamicPaymentMenus !== undefined"
              v-model="menu.dynamicPaymentMenus"
            >
              <option
                v-bind:value="getSelected(menu.dynamicPaymentMenus, thirdPartyIntegrationOption, 'id')"
                v-for="thirdPartyIntegrationOption in thirdPartyIntegrations"
                :key="thirdPartyIntegrationOption.id"
              >
                {{ thirdPartyIntegrationOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="menu-parent">Parent</label>
            <select class="form-control" id="menu-parent" data-cy="parent" name="parent" v-model="menu.parent">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="menu.parent && menuOption.id === menu.parent.id ? menu.parent : menuOption"
                v-for="menuOption in menus"
                :key="menuOption.id"
              >
                {{ menuOption.id }}
              </option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="v$.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./menu-update.component.ts"></script>
